package com.ecommerceproject1.ecommerce.Controller.User;
import com.ecommerceproject1.ecommerce.Service.Prodect.ProductService;
import com.ecommerceproject1.ecommerce.Service.User.ProfileService;
import com.ecommerceproject1.ecommerce.Service.User.UserService;
import com.ecommerceproject1.ecommerce.model.user.AddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProfileService profileService;




    @GetMapping("")
    public String userProfile(Principal principal, Model model){
        model.addAttribute("userData",userService.userInfofindByEmail(principal.getName()));
        return "user/Profile";
    }

    @GetMapping("/manage-address")
    public String manageAddress(Principal principal,Model model){
        model.addAttribute("user",userService.userInfofindByEmail(principal.getName()));
        return "user/manage-address";
    }

    @PostMapping("/newAddress")
    public String newAddress(Principal principal, RedirectAttributes red, @ModelAttribute AddressDTO address){
        String email=principal.getName();
        return profileService.newAddress(email,red,address);
    }




    @GetMapping("/deleteAddress/{addressId}")
    @ResponseBody
    public Map<String, Object> deleteAddress(@PathVariable Long addressId) {
        System.out.println("its woring pach");
        return profileService.deleteAddress(addressId);
    }

    @PutMapping("/profileImage")
    public ResponseEntity<Map<String,Object>> profileImage(@RequestParam("profileimage") MultipartFile profileimage,Principal principal){
        String name=principal.getName();
        Map<String, Object> response = new HashMap<>();
        System.out.println(name);
       boolean res= profileService.updateProfile(profileimage,name);
       if(res){
           return new ResponseEntity<>(response,HttpStatus.OK);
       }
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/updateName")
    public String updateName(Principal principal,RedirectAttributes red,@RequestParam("name") String name){

        return profileService.updateName(name,principal.getName(),red);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Map<String, Object>> changePassword(
            Principal principal,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword) {
        System.out.println("helo");

        Map<String, Object> response = new HashMap<>();

//         Validate old password
        if (!profileService.isOldPasswordCorrect(principal.getName(), oldPassword)) {
            response.put("success", false);
            response.put("message", "Incorrect old password");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Validate and change the password
        boolean passwordChanged = profileService.changePassword(principal.getName(), newPassword);

        if (passwordChanged) {
            response.put("success", true);
            response.put("message", "Password changed successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", "Failed to change password");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


}
