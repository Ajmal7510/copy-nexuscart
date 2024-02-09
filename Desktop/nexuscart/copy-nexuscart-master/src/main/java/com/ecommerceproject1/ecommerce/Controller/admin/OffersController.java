package com.ecommerceproject1.ecommerce.Controller.admin;

import com.ecommerceproject1.ecommerce.Service.Admin.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/admin/offers")
public class OffersController {

    @Autowired
    private OffersService offersService;

    @GetMapping("")
    public String getOffersPage(Model model){
        return offersService.viewOfferspage(model);
    }
    @PostMapping("/applyProductOffer")
    public String applyCoupon(){
     return null;
    }

    @PostMapping("/product/offer/add")
       public String addProductOffer(@RequestParam("productId")Long productId,@RequestParam("discountPrice")Float discountAmout){
        System.out.println(productId);
        System.out.println(discountAmout);
        return offersService.addProductOffer(productId,discountAmout);
    }

    @PostMapping("/category/offer/add")
    public String addCategoryOffer(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("discountAmount") Float discountAmount,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate expireDate
    ) {
        System.out.println("Category ID: " + categoryId);
        System.out.println("Discount Amount: " + discountAmount);
        System.out.println("Expire Date: " + expireDate);

        return offersService.addCategoryOffer(categoryId,discountAmount,expireDate);
    }


    @DeleteMapping("/product/offer/delete/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteProductOffer(@PathVariable("id") Long id) {
        // Your logic to delete the product offer by id
        try {
            offersService.deleteProductOfferById(id);
            Map<String, String> response = new HashMap<>();
            response.put("success", "true");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("success", "false");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @DeleteMapping("/category/offer/delete/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteCategoryOffer(@PathVariable("id") Long id) {
        // Your logic to delete the product offer by id
        try {
            offersService.deleteCategoryOffer(id);
            Map<String, String> response = new HashMap<>();
            response.put("success", "true");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("success", "false");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


//    public ResponseEntity<String> deleteCategoryOffer(@PathVariable Long id){
//        try {
//
//            offersService.deleteCategoryOffer(id);
//
//            return ResponseEntity.ok("Category offer deleted successfully");
//        } catch (Exception e) {
//
//            return ResponseEntity.status(500).body("Error deleting category offer: " + e.getMessage());
//        }
//    }
//







}
