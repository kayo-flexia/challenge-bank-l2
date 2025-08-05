package com.appbanco.demo.utils;

import com.appbanco.demo.dto.TransferenciaResponseDTO;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;

public class ComprobantePDFUtil {

    public static byte[] generarComprobantePDF(TransferenciaResponseDTO dto) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Comprobante de Transferencia"));
            document.add(new Paragraph("Fecha: " + dto.getFecha()));
            document.add(new Paragraph("Transferencia ID: " + dto.getTransferenciaId()));
            document.add(new Paragraph("Cuenta Origen: " + dto.getCuentaOrigenAlias()));
            document.add(new Paragraph("Cuenta Destino: " + dto.getDestinatarioAlias()));
            document.add(new Paragraph("Monto: $" + dto.getMonto()));
            document.add(new Paragraph("Descripción: " + dto.getDescripcion()));
            document.add(new Paragraph("Saldo Resultante: $" + dto.getSaldoResultante()));

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }
}

