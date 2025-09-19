package com.example.productSales.service;

import com.example.productSales.entity.Product;
import com.example.productSales.entity.Sale;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private ProductService productService;

    public byte[] generateProductsPdf() {
        List<Product> products = productService.getAllProducts(org.springframework.data.domain.Pageable.unpaged()).getContent();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            Font head = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            document.add(new Paragraph("Products", head));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 4, 6, 3, 2, 3});

            addTableHeader(table, new String[]{"ID", "Name", "Description", "Price", "Qty", "Revenue"});

            for (Product p : products) {
                table.addCell(String.valueOf(p.getId()));
                table.addCell(p.getName());
                table.addCell(p.getDescription() == null ? "" : p.getDescription());
                table.addCell(String.valueOf(p.getPrice()));
                table.addCell(String.valueOf(p.getQuantity()));

                BigDecimal revenue = BigDecimal.ZERO;
                for (Sale s : p.getSales()) {
                    BigDecimal saleRevenue = BigDecimal.valueOf(p.getPrice())
                            .multiply(BigDecimal.valueOf(s.getQuantity()));
                    revenue = revenue.add(saleRevenue);
                }
                table.addCell(revenue.toString());
            }



            document.add(table);
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    private void addTableHeader(PdfPTable table, String[] headers) {
        for (String h : headers) {
            PdfPCell header = new PdfPCell();
            header.setPhrase(new Phrase(h));
            header.setBackgroundColor(Color.LIGHT_GRAY);
            table.addCell(header);
        }
    }
}
