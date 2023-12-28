package com.ecommerceproject1.ecommerce.Service.Prodect;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import com.ecommerceproject1.ecommerce.model.product.Product_DTO;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Void addProduct(Products product);

    List<Products> findallProduct();

    void updateproduct(Long brandId,boolean isActive);

    Products findProductById(Long id);
    void deteleProductbyid(Long productId);
    void deleteImages(long id);

    String update(Long id, Product_DTO productDto) throws IOException;

    void save(Products products);
     void reduceStock(Long productId,int quantity);

}
