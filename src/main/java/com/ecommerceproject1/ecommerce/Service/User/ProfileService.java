package com.ecommerceproject1.ecommerce.Service.User;

import com.ecommerceproject1.ecommerce.Entity.user.Address;
import com.ecommerceproject1.ecommerce.model.user.AddressDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

@Service
public interface ProfileService {
    String newAddress(String email, RedirectAttributes red, AddressDTO address);

    ResponseEntity<String> newAddressCkecout(String email,AddressDTO address);

    public Map<String, Object> deleteAddress(Long addressId);

    Map<String, Object> profleimg(MultipartFile profileImg, String email);

    String updateName(String name,String email,RedirectAttributes red);

    boolean isOldPasswordCorrect(String email,String oldPassword);
    boolean changePassword(String email,String oldPassword);

    boolean updateProfile(MultipartFile profile,String eamil);
    void sentReferralLink(String friendEmail);
}
