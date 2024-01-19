package com.ecommerceproject1.ecommerce.Controller.admin;

import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/admin")
public class ReportController {

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/generatePdf")
    public void generatePdf(String reportType, Model model, HttpServletResponse response) throws IOException {
        // TODO: Implement logic to populate model with data based on the reportType
        model.addAttribute("reportType", reportType);

        // Thymeleaf template name
        String templateName = "admin/pdf_template"; // adjust the template path accordingly

        // Render the Thymeleaf template to HTML
        String renderedHtml = renderThymeleafTemplate(templateName, model);

        // Convert HTML to PDF using iText HTMLConverter
        try (OutputStream pdfOutputStream = response.getOutputStream()) {
            HtmlConverter.convertToPdf(renderedHtml, pdfOutputStream);
        }

        // Set response headers for PDF download
        // You can adjust the file name as needed
        String fileName = reportType + "_sales_report.pdf";
        response.setHeader("Content-Type", "application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    }

    private String renderThymeleafTemplate(String templateName, Model model) {
        Context context = new Context();
        context.setVariables(model.asMap());
        return templateEngine.process(templateName, context);
    }
}
