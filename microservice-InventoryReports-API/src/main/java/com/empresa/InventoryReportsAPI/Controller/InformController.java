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
public class InformController {

    @Autowired
    private IInformService informService;

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

    @GetMapping("/find-Inventory/{idInform}")
    public ResponseEntity<?> findInformByInventory(@PathVariable Long idInform) {
        return ResponseEntity.ok(informService.findById(idInform).orElse(new Inform()));
    }
}
