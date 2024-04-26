package com.empresa.InventoryReportsAPI.Service.Impl;

import com.empresa.InventoryReportsAPI.Client.InventoryClient;
import com.empresa.InventoryReportsAPI.DTO.InventoryDTO;
import com.empresa.InventoryReportsAPI.Entities.Inform;
import com.empresa.InventoryReportsAPI.HTTP.Response.InventoryByInformResponse;
import com.empresa.InventoryReportsAPI.Repository.InformRepository;
import com.empresa.InventoryReportsAPI.Service.IInformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (inform.getId() != null) {
            // Verificar si el informe ya existe en la base de datos
            Optional<Inform> existingInform = informRepository.findById(inform.getId());

            if (existingInform.isPresent()) {
                // Si el informe ya existe, actualizarlo
                Inform updatedInform = existingInform.get();
                updatedInform.setReportName(inform.getReportName());
                updatedInform.setDescription(inform.getDescription());
                updatedInform.setPdfContent(inform.getPdfContent());
                return informRepository.save(updatedInform);
            }
        }

        // Si el informe no existe, guardarlo
        Inform newInform = informRepository.save(inform);

        // Luego, obtener todos los datos del InventoryDTO desde el otro microservicio
        List<InventoryDTO> inventoryDTOList;
        try {
            inventoryDTOList = inventoryClient.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los datos del InventoryDTO", e);
        }

        // Aquí puedes procesar los datos del InventoryDTO como necesites
        StringBuilder pdfContentString = new StringBuilder();
        for (InventoryDTO inventoryDTO : inventoryDTOList) {
            pdfContentString.append("Nombre del producto: ").append(inventoryDTO.getName()).append("\n");
            pdfContentString.append("Descripción: ").append(inventoryDTO.getDescription()).append("\n");
            pdfContentString.append("Stock disponible: ").append(inventoryDTO.getStock()).append("\n");
            pdfContentString.append("Precio: ").append(inventoryDTO.getPrice()).append("\n");
            pdfContentString.append("Categoría: ").append(inventoryDTO.getCategory()).append("\n");
            pdfContentString.append("Proveedor: ").append(inventoryDTO.getProvider()).append("\n");
            pdfContentString.append("Fecha de creación: ").append(inventoryDTO.getFechaCreacion()).append("\n");
            pdfContentString.append("Fecha de actualización: ").append(inventoryDTO.getFechaActualizacion()).append("\n");
        }

        // Usar el servicio para crear el PDF
        byte[] pdfContent = pdfService.createSamplePDF(newInform.getReportName(), pdfContentString.toString());
        newInform.setPdfContent(pdfContent); // Asignar el contenido del PDF a la entidad

        // Actualizar el objeto Inform con el contenido del PDF en la base de datos
        return informRepository.save(newInform);
    }

    @Override
    public void deleteById(Long id) {
        informRepository.deleteById(id);
    }

    @Override
    public Inform generateAndSaveReport(String reportTitle, String content) {
        // Crear un nuevo Inform sin contenido de PDF
        Inform inform = new Inform();
        inform.setReportName(reportTitle);
        inform.setDescription(content);

        // Guardar el Inform en la base de datos
        Inform savedInform;
        try {
            savedInform = informRepository.save(inform);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el informe en la base de datos.", e);
        }

        // Obtener todos los datos de InventoryDTO
        List<InventoryDTO> allInventories = inventoryClient.findAll();

        // Aquí puedes procesar los datos de allInventories y generar el PDF
        // Por ejemplo, puedes construir una cadena con todos los datos de allInventories
        StringBuilder pdfContentString = new StringBuilder(content + "\n\n");
        for (InventoryDTO inventoryDTO : allInventories) {
            pdfContentString.append(inventoryDTO.toString()).append("\n");
            pdfContentString.append("Nombre del producto: ").append(inventoryDTO.getName()).append("\n");
            pdfContentString.append("Descripción: ").append(inventoryDTO.getDescription()).append("\n");
            pdfContentString.append("Stock disponible: ").append(inventoryDTO.getStock()).append("\n");
            pdfContentString.append("Precio: ").append(inventoryDTO.getPrice()).append("\n");
            pdfContentString.append("Categoría: ").append(inventoryDTO.getCategory()).append("\n");
            pdfContentString.append("Proveedor: ").append(inventoryDTO.getProvider()).append("\n");
            pdfContentString.append("Fecha de creación: ").append(inventoryDTO.getFechaCreacion()).append("\n");
            pdfContentString.append("Fecha de actualización: ").append(inventoryDTO.getFechaActualizacion()).append("\n");
        }

        // Usar el servicio para crear el PDF
        byte[] pdfContent = pdfService.createSamplePDF(reportTitle, pdfContentString.toString());

        // Comprobar si el contenido del PDF se generó correctamente
        if (pdfContent == null) {
            throw new RuntimeException("Error al generar el contenido del PDF.");
        }

        // Establecer el contenido del PDF en el Inform guardado
        savedInform.setPdfContent(pdfContent);

        // Actualizar el Inform en la base de datos con el contenido del PDF
        try {
            return informRepository.save(savedInform);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el informe en la base de datos con el contenido del PDF.", e);
        }
    }
}