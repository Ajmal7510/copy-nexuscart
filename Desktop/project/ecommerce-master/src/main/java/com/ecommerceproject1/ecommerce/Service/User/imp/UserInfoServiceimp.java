package com.ecommerceproject1.ecommerce.Service.User.imp;

import com.ecommerceproject1.ecommerce.Entity.user.*;
import com.ecommerceproject1.ecommerce.Exeption.UserAlredyExistException;
import com.ecommerceproject1.ecommerce.Repository.UserInfoRepository;
import com.ecommerceproject1.ecommerce.Repository.WalletRepository;
import com.ecommerceproject1.ecommerce.Service.User.CartService;
import com.ecommerceproject1.ecommerce.Service.User.UserInfoService;
import com.ecommerceproject1.ecommerce.Service.User.WishlistService;
import com.ecommerceproject1.ecommerce.Service.Verification.EmailService;
import com.ecommerceproject1.ecommerce.Service.Verification.RedisService;
import com.ecommerceproject1.ecommerce.model.user.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class
UserInfoServiceimp implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    UserDto userDto;



    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisService redisService;
    @Autowired
    private  HttpSession session;

    @Autowired
    private CartService cartService;
    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public void registerUser(UserDto user) throws UserAlredyExistException {

        Optional<UserInfo> userexist= Optional.ofNullable(userInfoRepository.findByEmail(user.getEmail()));
        if(userexist.isPresent()){

            throw new UserAlredyExistException("user with email " +user.getEmail()+"  Alredy exist");
        }

        String otp = generateOtp();

//         Save OTP to Redis
        redisService.saveOtp(user.getEmail(), otp);

        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), otp);


        user=userDto;

    }

    private String generateOtp() {

        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }


    @Override
    public void saveuser(UserDto userDto) {
        UserInfo user=new UserInfo();
        Cart cart=cartService.getcart();
        Wishlist wishlist=wishlistService.getWishlist();
        user.setWishlist(wishlist);
        user.setCart(cart);
        user.setName(userDto.getName());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRole("USER");
        cart.setUser(user);
        wishlist.setUser(user);
        user.setReferralCode(userDto.getName()+user.getUserId());
        userInfoRepository.save(user);

        UserInfo user1=userInfoRepository.findByEmail(userDto.getEmail());
        user1.setReferralCode(userDto.getName()+user.getUserId());
        userInfoRepository.save(user1);


    }

    public void resentOtp(String email){
        redisService.deleteOtp(email);
        String otp = generateOtp();
//         Save OTP to Redis
        redisService.saveOtp(email, otp);
        // Send verification email
        emailService.sendVerificationEmail(email, otp);

    }
    @Override
    public List<UserInfo> findByRole(String role) {
        return userInfoRepository.findByRoleOrderByNameAsc(role);
    }
    @Override
    public void updateUserEnabledStatus(Long userId, boolean isEnabled) {
        UserInfo user = userInfoRepository.findById(userId).orElse(null);
        isEnabled= !isEnabled;
        if (user != null) {
            user.setEnabled(isEnabled);
            userInfoRepository.save(user);
            invalidateUserSession(user.getEmail());
        }
    }

    @Override
    public void referral(String referralCode) {
        UserInfo user=userInfoRepository.findByReferralCode(referralCode);
        if(user==null){
            System.out.println("user is null");
        }
        Wallet wallet=walletRepository.findByUser(user);
        List<WalletHistory> walletHistories;

        if (wallet == null) {
            wallet = new Wallet();
            walletHistories = new ArrayList<>();
        } else {
            walletHistories = wallet.getWalletHistory();
        }
        WalletHistory walletHistory = new WalletHistory();
        walletHistory.setAddedAmount(100);
        walletHistory.setDepositOrWithdraw("Credited For Referral");
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        walletHistory.setAmountAddedTime(currentDateTime.format(formatter));
        walletHistory.setWallet(wallet);

        walletHistories.add(walletHistory);

        double totalAmount = walletHistories.stream().mapToDouble(WalletHistory::getAddedAmount).sum();


        wallet.setWalletTotalAmount(totalAmount);


        wallet.setWalletHistory(walletHistories);
        wallet.setUser(user);
        walletRepository.save(wallet);
    }

    private void invalidateUserSession(String username) {
        // Find all sessions associated with the user
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                if (userDetails.getUsername().equals(username)) {
                    // Invalidate each session
                    for (SessionInformation sessionInformation : sessionRegistry.getAllSessions(userDetails, false)) {
                        sessionInformation.expireNow();
                    }
                }
            }
        }
    }


}
