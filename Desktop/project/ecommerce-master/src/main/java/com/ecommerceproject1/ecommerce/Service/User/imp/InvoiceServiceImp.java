package com.ecommerceproject1.ecommerce.Service.User.imp;
import com.ecommerceproject1.ecommerce.Entity.user.Address;
import com.ecommerceproject1.ecommerce.Entity.user.OrderItem;
import com.ecommerceproject1.ecommerce.Entity.user.Orders;
import com.ecommerceproject1.ecommerce.Repository.OrderRepository;
import com.ecommerceproject1.ecommerce.Service.User.InvoiceService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class InvoiceServiceImp implements InvoiceService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public byte[] generatePdfContent(Long userId) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);


            Orders orders=orderRepository.findById(userId).orElse(null);

            Address address=orders.getAddress();
            List<OrderItem> products=orders.getOrderProducts();


            // Add content to the PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Brand Name
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(250, 700);
                contentStream.showText("NexusCart");
                contentStream.endText();

                // Billing Address
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText("Shipping Address...");
                contentStream.newLineAtOffset(50, 670);

                contentStream.showText("House Name: " + address.getAddress());
                contentStream.newLineAtOffset(0, -20); // Move to the next line

                contentStream.showText("City: " + address.getCity());
                contentStream.newLineAtOffset(0, -20); // Adjust spacing



                contentStream.showText("Pin: " + address.getPin());
                contentStream.newLineAtOffset(0, -20);

                contentStream.endText();

                // Add table headers
                float yPosition = 500;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("Product Name");
                contentStream.newLineAtOffset(100, 0);
                contentStream.newLineAtOffset(100, 0);
                contentStream.newLineAtOffset(100, 0);
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("Product Price");
                contentStream.endText();

                // Move to the next line for the data
                yPosition -= 20;
                // Add table data
                for (OrderItem product : products) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(product.getProduct().getProductName());
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText(String.valueOf(product.getQuantity()));
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText(String.format("$%.2f", product.getProduct().getPrice()));
                    contentStream.endText();


                    // Move to the next line for the next product
                    yPosition -= 20;
                }

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("Total Amount:");
                contentStream.newLineAtOffset(150, 0);
                contentStream.newLineAtOffset(150, 0);
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(String.format("$%.2f", orders.getTotalAmount()));
                contentStream.endText();
                contentStream.close();


//                document.save(baos);
            }


            // Convert the PDF document to a byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your needs
            return new byte[0]; // Return an empty array or handle the error differently
        }
    }
}
