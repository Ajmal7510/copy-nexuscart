package com.ecommerceproject1.ecommerce.Service.User;

import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import com.ecommerceproject1.ecommerce.Exeption.UserAlredyExistException;
import com.ecommerceproject1.ecommerce.model.user.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserInfoService {
//    void saveuser(UserInfo user);

    public void registerUser(UserDto user) throws UserAlredyExistException;

    void saveuser(UserDto userDto);
    public void resentOtp(String email);

    public List<UserInfo> findByRole(String role);
   void updateUserEnabledStatus( Long userId,boolean isEnabled);

   void referral(String referralCode);

}
