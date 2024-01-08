package com.ecommerceproject1.ecommerce.Controller.admin;

import com.ecommerceproject1.ecommerce.Repository.BannerRepository;
import com.ecommerceproject1.ecommerce.Service.Admin.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/admin/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("")
    public String banner(Model model){
        return bannerService.bannerPage(model);
    }

    @PostMapping("/addbanner")
    public String addbanner(@RequestParam("images") MultipartFile[] images,
                            @RequestParam("description") String description,
                            RedirectAttributes red) throws IOException {
        System.out.println("scuucess");

       return bannerService.addbanner(images,description,red);


    }


    @PutMapping("/activate/{bannerId}")
    public ResponseEntity<String> activateBanner(@PathVariable Long bannerId) {



        try {
            bannerService.activateBanner(bannerId);
            return ResponseEntity.ok("Banner activated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/delete/{bannerId}")
    public ResponseEntity<String> deleteBanner(@PathVariable Long bannerId) {

        try {
            bannerService.deleteBanner(bannerId);
            return ResponseEntity.ok("Banner activated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
