package com.appbanco.demo.controller;

import com.appbanco.demo.dto.TransferenciaRequestDTO;
import com.appbanco.demo.dto.TransferenciaResponseDTO;
import com.appbanco.demo.service.TransferenciaService;
import com.appbanco.demo.utils.ComprobantePDFUtil;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @PostMapping
    public TransferenciaResponseDTO realizarTransferencia(@RequestBody TransferenciaRequestDTO dto,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return transferenciaService.realizarTransferencia(dto, username);
    }

    @GetMapping("/comprobante/{transferenciaId}")
    public ResponseEntity<byte[]> descargarComprobante(@PathVariable Long transferenciaId, @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        TransferenciaResponseDTO comprobante = transferenciaService.obtenerComprobante(transferenciaId, username);

        byte[] pdfBytes = ComprobantePDFUtil.generarComprobantePDF(comprobante);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("Comprobante_Transferencia_" + transferenciaId + ".pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}

