package com.ecommerceproject1.ecommerce.Service.Prodect.impl;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import com.ecommerceproject1.ecommerce.Exeption.ResourceNotFound;
import com.ecommerceproject1.ecommerce.Repository.ProductRepository;
import com.ecommerceproject1.ecommerce.Service.Prodect.CategoryService;
import com.ecommerceproject1.ecommerce.Service.Prodect.ProductService;
import com.ecommerceproject1.ecommerce.model.product.Product_DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductServiceimp implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;


    @Override
    public Void addProduct(Products product) {
        productRepository.save(product);

        return null;
    }

    @Override
    public List<Products> findallProduct() {
        return productRepository.findAll();
    }

    @Override
    public void updateproduct(Long brandId, boolean isActive) {

        Products product=productRepository.findById(brandId).orElse(null);
        isActive=!isActive;
        if(product!=null){
            product.setActive(isActive);
            productRepository.save(product);
        }

    }

    @Override
    public Products findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void deteleProductbyid(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void deleteImages(long id) {
        Products product = productRepository.findById(id).orElse(null);
        String UPLOAD_DIR = "C:\\Project 1\\ecommerce\\src\\main\\resources\\static\\uploads\\";

        if (product != null) {
            String[] imagePathString = product.getImagesPath();
            for (String s : imagePathString) {
                String path = UPLOAD_DIR + s;
                Path filePath = Paths.get(path);
                try {
                    Files.delete(filePath);
                } catch (IOException e) {
                    // Handle IOException, e.g., log the error
                    throw new RuntimeException("Error deleting file: " + e.getMessage());
                }
            }
        } else {
            throw new ResourceNotFound("Product not found");
        }
    }

    @Override
    public String update(Long id, Product_DTO productDto) throws IOException {
//        Products products=productRepository.findById(id).orElse(null);
//        if(products==null){
//            throw new ResourceNotFound(" ");
//        }
//        if(!productDto.getProductName().isBlank()){
//            products.setProductName(productDto.getProductName());
//        }
//        if(!productDto.getDescription().isBlank()){
//            products.setDescription(productDto.getDescription());
//        }
//        products.setPrice(productDto.getPrice());
//        products.setStock(productDto.getStock());
//        products.setCategory(categoryService.getCategoryById(productDto.getCategoryId()));
//        products.setBrand(categoryService.getBrandById(productDto.getBrandId()));
//
//        String UPLOAD_DIR="C:\\Project 1\\ecommerce\\src\\main\\resources\\static\\uploads\\";
//
////        MultipartFile[] files = productDto.getProductImages();
////        for (int i = 0; i < productDto.getProductImages().length; i++) {
////            String path=files[i].getOriginalFilename();
////           if(!products.getImagesPath()[i].equals(path)){
////               Path path1=Paths.get(UPLOAD_DIR+path);
////               Files.delete(path1);
////               products.getImagesPath()[i]=path;
////               files[i].transferTo(new File(UPLOAD_DIR +path));
////
////           }
////
////
////            }
//
//
//        productRepository.save(products);




//        red.addFlashAttribute("update","product updated successfully");
        return "redirect:/admin/product";
    }

    @Override
    public void save(Products products) {
        productRepository.save(products);
    }

    @Override
    public void reduceStock(Long productId,int quantity) {
        Products products = productRepository.findById(productId).orElse(null);

        products.setStock(products.getStock()-quantity);
        productRepository.save(products);
    }
}
