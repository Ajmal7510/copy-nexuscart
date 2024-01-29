package com.ecommerceproject1.ecommerce.Exeption;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class RegistractionExceptionHandler {
    @ExceptionHandler(UserAlredyExistException.class)
    public String userAlredyExist(RedirectAttributes redirectAttributes,UserAlredyExistException ex){
        redirectAttributes.addFlashAttribute("UserAlredyExist",ex.getMessage());


        return "redirect:/signup";
    }

    @ExceptionHandler(UserNotFountException.class)
    public String userNotFount(RedirectAttributes redirectAttributes,UserNotFountException ex){
        redirectAttributes.addFlashAttribute("UserNotFount",ex.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String Customusernot(RedirectAttributes redirectAttributes,UsernameNotFoundException ex){
        redirectAttributes.addFlashAttribute("userNotFount","Email and password missmatch");
        return "redirect:/login";
    }
    @ExceptionHandler(DisabledException.class)
    public String userDisableExepation(RedirectAttributes red,DisabledException ex){
        red.addFlashAttribute("UserBlocked","This Account has been blocked");
        return "redirect:/login";
    }



    @ExceptionHandler(ResourceNotFound.class)
    public String handleResourceNotFoundException(ResourceNotFound ex){
        return "404";
    }

}
