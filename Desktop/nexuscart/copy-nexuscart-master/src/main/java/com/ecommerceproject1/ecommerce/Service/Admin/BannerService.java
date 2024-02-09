package com.ecommerceproject1.ecommerce.Service.Admin;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Service
public interface BannerService {
    String addbanner(MultipartFile[] images, String description, RedirectAttributes red) throws IOException;
    String bannerPage(Model model);
    void activateBanner(Long bannerId);
    void deleteBanner(Long bannerId);
}
