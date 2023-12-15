package com.ecommerceproject1.ecommerce.Controller.admin;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import com.ecommerceproject1.ecommerce.Exeption.ResourceNotFound;
import com.ecommerceproject1.ecommerce.Service.Prodect.CategoryService;
import com.ecommerceproject1.ecommerce.Service.Prodect.ProductService;
import com.ecommerceproject1.ecommerce.model.product.Product_DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller

@RequestMapping("/admin/product")
public class ProductController {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;


    @GetMapping("")
    public String prodect(Model model){
        model.addAttribute("products",productService.findallProduct());
        return "admin/product";
    }
    @GetMapping("/addproduct")
    public String addproduct(Model model){
        model.addAttribute("categories",categoryService.findAllCategories());
        model.addAttribute("brands",categoryService.finallbyBrand());


        return "admin/addproduct";
    }


    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute @Validated
                                 Product_DTO productDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            return "redirect:admin/addproduct";
        }

        // Save the product images to the static path
        String UPLOAD_DIR="C:\\Project 1\\ecommerce\\src\\main\\resources\\static\\uploads\\";
        MultipartFile[] files = productDTO.getProductImages();
//        String[] images = productDTO.getImages_path();

        for (MultipartFile file : files) {
            file.transferTo(new File(UPLOAD_DIR + file.getOriginalFilename()));
        }

        // Create a Products entity from the DTO
        Products product = new Products();
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setStock(productDTO.getStock());
        product.setPrice(productDTO.getPrice());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()));
        product.setBrand(categoryService.getBrandById(productDTO.getBrandId()));

        // Set images path (you might want to modify this based on your actual logic)
        for (int i = 0; i < productDTO.getProductImages().length; i++) {
            product.getImagesPath()[i] = productDTO.getProductImages()[i].getOriginalFilename();
        }
        System.out.println(productDTO.getProductImages()[0].getOriginalFilename());


        // Save the product to the database
        productService.addProduct(product);

        redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
        return "redirect:/admin/product";
    }


    @PostMapping("/block")
    public String prodectBleck(@RequestParam("productId") Long productId , @RequestParam("isActive") boolean isActive,RedirectAttributes red){
        productService.updateproduct(productId,isActive);
        return "redirect:/admin/product";
    }

    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes red){
        productService.deleteImages(id);
        productService.deteleProductbyid(id);

        red.addFlashAttribute("deleted","product delete success full");

        return "redirect:/admin/product";

    }

    @GetMapping("/editproduct/{id}")
    public String editproduct(@PathVariable("id")Long id,Model model){
        model.addAttribute("product",productService.findProductById(id));
        model.addAttribute("brands",categoryService.finallbyBrand());
        model.addAttribute("categories",categoryService.findAllCategories());
        return "admin/editproduct";
    }

    @PostMapping("/editProduct/{id}")
    public String eitProduct(@PathVariable(name="id") Long productId,
                             @RequestParam(name="brandId",required = false) Long brandId,
                             @RequestParam(name="categoryId",required = false) Long categoryId,
                             @RequestParam(name="productName",required = false) String productName,
                             @RequestParam(name="description",required = false) String description,
                             @RequestParam(name="productImages",required = false) MultipartFile[] imageFiles,
                             @RequestParam(name="productPrice",required = false) Float price,
                             @RequestParam(name="productStock",required = false) Integer stock

    ) throws IOException {
        Products products=productService.findProductById(productId);
        if(products==null){
            throw new ResourceNotFound("");
        }
        if(!productName.isBlank()){
            products.setProductName(productName);
        }
        if(description.isBlank()){
            products.setDescription(description);
        }
        if(stock!=null){
            products.setStock(stock);
        }
        if(price!=null){
           products.setPrice(price);
        }
        if (imageFiles == null) {
            System.out.println("image is null");
        } else {
            String imagepath = imageFiles[0].getOriginalFilename();
            System.out.println("hi" + imagepath);
        }
        String UPLOAD_DIR="C:\\Project 1\\ecommerce\\src\\main\\resources\\static\\uploads\\";


        if(imageFiles[0].getOriginalFilename().isBlank()){
            System.out.println("this is blank");
        }
        for(int i=0;i<imageFiles.length;i++){
            if(!imageFiles[i].getOriginalFilename().isBlank()){
                imageFiles[i].transferTo(new File(UPLOAD_DIR + imageFiles[i].getOriginalFilename()));
                Path filePath = Paths.get(UPLOAD_DIR+products.getImagesPath()[i]);

                try {
                    Files.delete(filePath);
                }catch (IOException e){
                    throw new IOException(e.getMessage());
                }
                products.getImagesPath()[i]=imageFiles[i].getOriginalFilename();
            }
        }






        products.setCategory(categoryService.findById(categoryId));
        products.setBrand(categoryService.findByBrandId(brandId));
        productService.save(products);
        return "redirect:/admin/product";
    }

}

