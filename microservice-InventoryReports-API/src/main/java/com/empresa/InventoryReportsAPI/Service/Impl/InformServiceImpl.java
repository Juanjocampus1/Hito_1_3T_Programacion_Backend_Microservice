package com.empresa.InventoryReportsAPI.Service.Impl;

import com.empresa.InventoryReportsAPI.Client.InventoryClient;
import com.empresa.InventoryReportsAPI.DTO.InventoryDTO;
import com.empresa.InventoryReportsAPI.Entities.Inform;
import com.empresa.InventoryReportsAPI.HTTP.Response.InformByInventoryResponse;
import com.empresa.InventoryReportsAPI.Repository.InformRepository;
import com.empresa.InventoryReportsAPI.Service.IInformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class InformServiceImpl implements IInformService {

    @Autowired
    private InformRepository informRepository;

    @Autowired
    private PDFService pdfService;

    @Autowired
    private InventoryClient inventoryClient;

    @Override
    public List<Inform> findAll() {
        return (List<Inform>) informRepository.findAll();
    }

    @Override
    public Optional<Inform> findById(Long id) {
        return informRepository.findById(id);
    }

    @Override
    public Inform save(Inform inform) {
        // Usar el servicio para crear el PDF
        byte[] pdfContent = pdfService.createSamplePDF("Informe de Inventario", "Contenido del informe.");
        inform.setPdfContent(pdfContent); // Asignar el contenido del PDF a la entidad

        return informRepository.save(inform); // Guardar en la base de datos
    }

    @Override
    public void deleteById(Long id) {
        informRepository.deleteById(id);
    }

    @Override
    public InformByInventoryResponse findInformByInventory(Long idInform) {

        Inform inform = informRepository.findById(idInform).orElse(new Inform());

        List<InventoryDTO> inventorieDTOList = inventoryClient.findAllByIdInform(idInform);

        return InformByInventoryResponse.builder()
                .InformTitle(inform.getReportName())
                .PDFContent(inform.getPdfContent())
                .inventories(inventorieDTOList)
                .build();
    }
}