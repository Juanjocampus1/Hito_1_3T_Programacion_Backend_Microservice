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
        // Obtener los datos del InventoryDTO desde el otro microservicio
        List<InventoryDTO> inventoryDTOList = inventoryClient.findAllByIdInform(inform.getId());

        // Construir el contenido del PDF utilizando los datos del InventoryDTO
        StringBuilder pdfContentString = new StringBuilder("Informe de Inventario\n\n");
        for (InventoryDTO inventoryDTO : inventoryDTOList) {
            pdfContentString.append("Nombre del producto: ").append(inventoryDTO.getName()).append("\n");
            pdfContentString.append("Descripción: ").append(inventoryDTO.getDescription()).append("\n");
            pdfContentString.append("Stock disponible: ").append(inventoryDTO.getStock()).append("\n");
            pdfContentString.append("Precio: ").append(inventoryDTO.getPrice()).append("\n");
            pdfContentString.append("Categoría: ").append(inventoryDTO.getCategory()).append("\n");
            pdfContentString.append("Proveedor: ").append(inventoryDTO.getProvider()).append("\n");
            pdfContentString.append("Fecha de creación: ").append(inventoryDTO.getFechaCreacion()).append("\n");
            pdfContentString.append("Fecha de actualización: ").append(inventoryDTO.getFechaActualizacion()).append("\n");
            pdfContentString.append("ID de informe: ").append(inventoryDTO.getIdInform()).append("\n\n");
        }

        // Usar el servicio para crear el PDF
        byte[] pdfContent = pdfService.createSamplePDF("Informe de Inventario", pdfContentString.toString());
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