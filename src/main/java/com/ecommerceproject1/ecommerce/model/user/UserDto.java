package com.ecommerceproject1.ecommerce.model.user;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

@Component
@SessionAttributes("userSignupInfo")
@Data
public class UserDto {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

}
