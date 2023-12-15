package com.ecommerceproject1.ecommerce.Controller;
import com.ecommerceproject1.ecommerce.Service.User.UserInfoService;
import com.ecommerceproject1.ecommerce.Service.Verification.RedisService;
import com.ecommerceproject1.ecommerce.model.user.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Set;

@Controller
public class MainController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private HttpSession session;


  @GetMapping("/login")
  public String login(Principal principal,HttpSession session,Model model){
      String loginerror= (String) session.getAttribute("loginError");
      if(principal!=null) {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
          if (roles.contains("ADMIN")) {

              return "redirect:admin/dashboard";
          } else if (roles.contains("USER")) {

              return "redirect:user/shop";
          }  }
      if(loginerror!=null){
          model.addAttribute("loginError",loginerror);
          session.removeAttribute("loginError");
      }
      return "login";
  }
   @GetMapping("/signup")
   public String signuppage(Model model,Principal principal){
       if(principal!=null) {
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
           if (roles.contains("ADMIN")) {

               return "redirect:admin/dashboard";
           } else if (roles.contains("USER")) {

               return "redirect:user/shop";
           }  }
        model.addAttribute("userDto",new UserDto());
        return "signup";
   }


   @PostMapping("/signup")
   public String signup(@ModelAttribute("userSignupInfo") UserDto user, Model model){
       session.setAttribute("userDto", user);
       redisService.deleteOtp(user.getEmail());
      userInfoService.registerUser(user);
      model.addAttribute("email",user.getEmail());
       return "otp";
   }


        @PostMapping("/toOtp")
        public String handleOtpVerification(
                @RequestParam("otp1") int otp1,
                @RequestParam("otp2") int otp2,
                @RequestParam("otp3") int otp3,
                @RequestParam("otp4") int otp4,
                @RequestParam("email")String email,
                Model model, RedirectAttributes red) {
            // Assuming you want to concatenate the individual digits into a single string
            String otp = String.valueOf(otp1) + String.valueOf(otp2) + String.valueOf(otp3) + String.valueOf(otp4);
            String storedOtp = redisService.getOtp(email);
            if (storedOtp != null && storedOtp.equals(otp)) {
                // Email verified successfully
                redisService.deleteOtp(email);
                UserDto userDto = (UserDto) session.getAttribute("userDto");
                red.addFlashAttribute("verificationStatus", "Email verification successful");
                userInfoService.saveuser(userDto);
                return "redirect:/login";
            } else {
                model.addAttribute("verificationStatus", "Invalid OTP");
                model.addAttribute("email", email);

                return "otp";
            }
        }

        @GetMapping("/resendOtp")
        public String resentOtp(@RequestParam String email, Model model){
      userInfoService.resentOtp(email);
      model.addAttribute("email",email);

      return "otp";
        }


    }
