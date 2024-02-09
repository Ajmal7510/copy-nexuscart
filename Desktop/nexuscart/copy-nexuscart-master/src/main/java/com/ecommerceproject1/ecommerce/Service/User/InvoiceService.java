package com.ecommerceproject1.ecommerce.Service.User;

import org.springframework.stereotype.Service;

@Service
public interface InvoiceService {
    byte[] generatePdfContent(Long userid);
}
