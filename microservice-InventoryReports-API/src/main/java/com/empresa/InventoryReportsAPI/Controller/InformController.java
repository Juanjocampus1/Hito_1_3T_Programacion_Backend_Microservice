package com.empresa.InventoryReportsAPI.Controller;

import com.empresa.InventoryReportsAPI.DTO.InformDTO;
import com.empresa.InventoryReportsAPI.Entities.Inform;
import com.empresa.InventoryReportsAPI.Service.IInformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inform")
//@CrossOrigin(origins = "http://13.48.29.69:8081")
@CrossOrigin(origins = "http://localhost:8081")
public class InformController {

    @Autowired
    private IInformService informService;

    @PostMapping("/generate")
    public Inform generateAndSaveReport(@RequestBody InformDTO informDTO) {
        // Crear un nuevo informe con el título y la descripción
        Inform inform = new Inform();
        inform.setReportName(informDTO.getReportName());
        inform.setDescription(informDTO.getDescription());

        // Generar el contenido del PDF
        String pdfContentString = informService.generateAndSaveReport(
                informDTO.getReportName(),
                informDTO.getDescription()).getDescription();

        // Convertir el contenido del PDF a byte[]
        byte[] pdfContentBytes = pdfContentString.getBytes();

        // Asignar el contenido del PDF al informe
        inform.setPdfContent(pdfContentBytes);

        // Guardar el informe en la base de datos
        return informService.save(inform);
    }

    @GetMapping("/allreports")
    public List<Inform> getAllInforms() {
        return informService.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody InformDTO informDTO) throws URISyntaxException {

        if (informDTO.getReportName() == null || informDTO.getReportName().isBlank()) {
            return ResponseEntity.badRequest().body("Report name cannot be blank");
        }
        else {
            Inform inform = Inform.builder()
                    .reportName(informDTO.getReportName())
                    .description(informDTO.getDescription())
                    .build();
            Inform savedInform = informService.save(inform);
            return ResponseEntity.created(new URI("/api/inform/save" + savedInform.getId())).build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<InformDTO> informList = informService.findAll()
                .stream()
                .map(inform -> InformDTO.builder()
                        .id(inform.getId())
                        .pdfContent(inform.getPdfContent())
                        .reportName(inform.getReportName())
                        .description(inform.getDescription())
                        .build())
                .toList();
        return ResponseEntity.ok(informList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Optional<Inform> informOptional = informService.findById(id);

        if (informOptional.isPresent()) {
            Inform inform = informOptional.get();

            InformDTO informDTO = InformDTO.builder()
                    .id(inform.getId())
                    .pdfContent(inform.getPdfContent())
                    .reportName(inform.getReportName())
                    .description(inform.getDescription())
                    .build();
            return ResponseEntity.ok(informDTO);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        if (id != null) {
            informService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

}
