package com.ecommerceproject1.ecommerce.Service.Admin;

import com.ecommerceproject1.ecommerce.Entity.Admin.Banner;
import com.ecommerceproject1.ecommerce.Exeption.ResourceNotFound;
import com.ecommerceproject1.ecommerce.Repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

@Service
public class BannerServiceImp implements BannerService {
    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public String addbanner(MultipartFile[] images, String description, RedirectAttributes red) throws IOException {
//       try {
           System.out.println("working part 1");
           int i=0;
           Banner banner=new Banner();
           System.out.println("working part2");
           String UPLOAD_DIR="C:\\Project 1\\ecommerce\\src\\main\\resources\\static\\banner\\";

           for(MultipartFile img:images){
               String UUI = UUID.randomUUID().toString();
               banner.getImagesPath()[i] = UUI+img.getOriginalFilename();
               img.transferTo(new File(UPLOAD_DIR +UUI+ img.getOriginalFilename()));
               i++;
           }
           System.out.println("working part 5");
           banner.setDescription(description);
           System.out.println("working part 6");
           bannerRepository.save(banner);
           System.out.println("working part 7");

           red.addFlashAttribute("success"," banner added successfully");
           return "redirect:/admin/banner";
//       }
//       catch (Exception e){
//           e.printStackTrace();
//           red.addFlashAttribute("error"," error");
//           System.out.println("showing error"+e);
//           return "redirect:/admin/banner";
//       }
    }

    @Override
    public String bannerPage(Model model) {
        List<Banner> banners=bannerRepository.findAllByOrderByBannerIdAsc();

        model.addAttribute("banners",banners);
        return "admin/banner";
    }

    @Override
    public void activateBanner(Long bannerId) {
        Banner banner=bannerRepository.findFirstByIsActive(true);
        banner.setActive(false);
        bannerRepository.save(banner);

        Banner banner1=bannerRepository.findById(bannerId).orElse(null);
        if(banner1==null) throw new ResourceNotFound("helo");
        banner1.setActive(true);
        bannerRepository.save(banner1);

    }

    @Override
    public void deleteBanner(Long bannerId) {
        Banner banner1=bannerRepository.findById(bannerId).orElse(null);
        if(banner1==null) throw new ResourceNotFound("helo");
        bannerRepository.delete(banner1);

    }
}
