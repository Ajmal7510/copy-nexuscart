package com.ecommerceproject1.ecommerce.Controller.admin;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Brands;
import com.ecommerceproject1.ecommerce.Entity.Prodect.Categories;
import com.ecommerceproject1.ecommerce.Service.Offers.CategoryOfferService;
import com.ecommerceproject1.ecommerce.Service.Prodect.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryOfferService categoryOfferService;

    @GetMapping("")
    public String category(Model model){
        model.addAttribute("categories",categoryService.findAllCategories());
        model.addAttribute("brands",categoryService.finallbyBrand());
        return "admin/category";
    }
    @PostMapping("/saveCategory")
    public String saveCategory(
            @RequestParam("categoryName") String categoryName,
            RedirectAttributes red
    ){
        if(!categoryName.isBlank()){
            if(categoryService.CategoryExist(categoryName)){
                red.addFlashAttribute("categoryexist","Category Alredy Exist");
                return "redirect:/admin/category";
            }
            Categories categories=new Categories();
            categories.setCategoryName(categoryName);
            categoryService.SaveCategory(categories);
            categoryOfferService.createCategoryOffer(categories);
            red.addFlashAttribute("categoryAddSuccess","Successfully Add New Category");
            return "redirect:/admin/category";
        }

        red.addFlashAttribute("inputError", "Please Enter The Category Name");
        return "redirect:/admin/category";
    }


    @PostMapping("/deleteCategory/{id}")
    public String deleteCategory(RedirectAttributes red,@PathVariable("id") Long brandId){
        categoryService.deleteCategory(brandId);

        red.addFlashAttribute("delmessage","Category removed from the list successfully");
        return "redirect:/admin/category";

    }


    //brnd

    @PostMapping("/addbrand")
    public String addBrand(RedirectAttributes red, @RequestParam("brandName") String brandName) {
        if (!brandName.isBlank()) {
            if (categoryService.brandExists(brandName)) {
                red.addFlashAttribute("brandexist", "Brand Already Exists");
                return "redirect:/admin/category";
            }

            Brands brand = new Brands();
            brand.setBrandName(brandName);
            categoryService.SaveBrand(brand);

            red.addFlashAttribute("brandAddSuccess", "Successfully Added New Brand");
            return "redirect:/admin/category";
        }

        red.addFlashAttribute("brandinputError", "Please Enter The Brand Name Properly");
        return "redirect:/admin/category";
    }
    @PostMapping("/deletebrand/{id}")
    public String deleteBrand(RedirectAttributes red,@PathVariable("id") Long brandId){
        categoryService.deleteBrand(brandId);

        red.addFlashAttribute("delmessagebrand","Brand removed from the list successfully");
        return "redirect:/admin/category";

    }






}
