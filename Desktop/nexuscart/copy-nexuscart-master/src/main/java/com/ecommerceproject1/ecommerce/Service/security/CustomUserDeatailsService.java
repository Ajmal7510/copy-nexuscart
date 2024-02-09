package com.ecommerceproject1.ecommerce.Service.security;


import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import com.ecommerceproject1.ecommerce.Exeption.UserNotFountException;
import com.ecommerceproject1.ecommerce.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDeatailsService  implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException,DisabledException {
        System.out.println("helo");

        UserInfo user = userInfoRepository.findByEmail(email);
        if(user==null){
            throw new UsernameNotFoundException("user not fount");
        }
        if(!user.isEnabled()){
            throw new DisabledException("User is disabled");
        }
        return new CustomUserDetails(user);
    }
}
