package com.empresa.InventoryReportsAPI.Service.Impl;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

@Service
public class PDFService {

    public byte[] createSamplePDF(String reportTitle, String content) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph(reportTitle));
            document.add(new Paragraph(content));
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace(); // Manejo de excepciones según tus requerimientos
        }

        return outputStream.toByteArray();
    }
}