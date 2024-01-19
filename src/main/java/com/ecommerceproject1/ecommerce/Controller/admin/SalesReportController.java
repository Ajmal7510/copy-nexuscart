package com.ecommerceproject1.ecommerce.Controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/sales-report")
public class SalesReportController {

    @GetMapping("")
    public String getSalesReport(Model model){
        return "admin/salesReport";
    }
}
