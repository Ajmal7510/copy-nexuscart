package com.ecommerceproject1.ecommerce.Service.User.imp;

import com.ecommerceproject1.ecommerce.Entity.user.Address;
import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import com.ecommerceproject1.ecommerce.Repository.AddressRepository;
import com.ecommerceproject1.ecommerce.Repository.UserInfoRepository;
import com.ecommerceproject1.ecommerce.Service.User.ProfileService;
import com.ecommerceproject1.ecommerce.model.user.AddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ProfileServiceImp implements ProfileService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public String newAddress(String email, RedirectAttributes red, AddressDTO address) {
        System.out.println("working new address");
        UserInfo user=userInfoRepository.findByEmail(email);
        System.out.println("working user finding ");
        Address addressDetail=addressManagement(address);
        user.getUserAddresses().add(addressDetail);
        System.out.println("working add adrress");
        userInfoRepository.save(user);
        System.out.println("working user save");


        red.addFlashAttribute("addressAdded","successfully Add address");


        return "redirect:/user/profile/manage-address";
    }

    @Override
    public ResponseEntity<String> newAddressCkecout(String email, AddressDTO address) {
            try {
                System.out.println("working new address");
                UserInfo user=userInfoRepository.findByEmail(email);
                System.out.println("working user finding ");
                Address addressDetail=addressManagement(address);
                user.getUserAddresses().add(addressDetail);
                System.out.println("working add adrress");
                userInfoRepository.save(user);
                System.out.println("working user save");
                return ResponseEntity.ok("Address added successfully!");
            } catch (Exception e) {
                // Log the exception for debugging purposes
                e.printStackTrace();

                // Return an error response
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding the address");
            }
    }

    @Override
    public Map<String, Object> deleteAddress(Long addressId) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Attempt to delete the address
            addressRepository.deleteById(addressId);

            // If deletion is successful
            response.put("success", true);
            response.put("message", "Address deleted successfully");
        } catch (Exception e) {
            // If an exception occurs during deletion
            response.put("success", false);
            response.put("message", "Error deleting address");
            response.put("error", e.getMessage()); // Optionally, you can include the specific error message

            // Log the exception for debugging purposes
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public Map<String, Object> profleimg(MultipartFile profileImg, String email) {
        UserInfo user=userInfoRepository.findByEmail(email);
        String UPLOAD_DIR="C:\\Project 1\\ecommerce\\src\\main\\resources\\static\\uploads\\";

        Map<String, Object> response = new HashMap<>();
        try {
            profileImg.transferTo(new File(UPLOAD_DIR + profileImg.getOriginalFilename()));
            user.setProfilePhoto(profileImg.getOriginalFilename());
            userInfoRepository.save(user);
            response.put("success", true);
            response.put("message", "profileUpdated successfully");
        } catch (Exception e) {
            // If an exception occurs during deletion
            response.put("success", false);
            response.put("message", "Error deleting address");
            response.put("error", e.getMessage()); // Optionally, you can include the specific error message
            // Log the exception for debugging purposes
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public String updateName(String name, String email, RedirectAttributes red) {
        if(!name.isBlank()) {
           UserInfo user=userInfoRepository.findByEmail(email);
           user.setName(name);
           userInfoRepository.save(user);
           red.addFlashAttribute("updatName","successfullychangedname");
           return "redirect:/user/profile";
        }

        return "redirect:/user/profile";
    }

    @Override
    public boolean isOldPasswordCorrect(String email, String oldPassword) {
        UserInfo user=userInfoRepository.findByEmail(email);
        return bCryptPasswordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public boolean changePassword(String email, String oldPassword) {
        UserInfo user=userInfoRepository.findByEmail(email);
        String oldp=bCryptPasswordEncoder.encode(oldPassword);
        user.setPassword(oldp);
        userInfoRepository.save(user);
        return true;
    }

    @Override
    public boolean updateProfile(MultipartFile profile, String eamil) {
        String UPLOAD_DIR="C:\\Project 1\\ecommerce\\src\\main\\resources\\static\\profile\\";

        UserInfo user=userInfoRepository.findByEmail(eamil);

        try {
            profile.transferTo(new File(UPLOAD_DIR + profile.getOriginalFilename()));
            user.setProfilePhoto(profile.getOriginalFilename());
            userInfoRepository.save(user);

           return true;
        } catch (Exception e) {
            // If an exception occurs during deletion

            e.printStackTrace();
        }

        return false;
    }


    public Address addressManagement(AddressDTO address) {

        Address addressDetails = new Address();
        addressDetails.setAddress(address.getAddress());
        addressDetails.setName(address.getName());
        addressDetails.setState(address.getState());
        addressDetails.setCity(address.getCity());
        addressDetails.setPin(address.getPin());
        addressDetails.setMobile(address.getMobile());
        return addressDetails;
    }
}
