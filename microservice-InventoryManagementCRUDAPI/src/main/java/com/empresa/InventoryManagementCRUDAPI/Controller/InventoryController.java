package com.empresa.InventoryManagementCRUDAPI.Controller;

import com.empresa.InventoryManagementCRUDAPI.DTO.InventoryDTO;
import com.empresa.InventoryManagementCRUDAPI.Entities.Inventory;
import com.empresa.InventoryManagementCRUDAPI.Service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/crud")
//@CrossOrigin(origins = "http://13.48.29.69:8081")
@CrossOrigin(origins = "http://localhost:8081")
public class InventoryController {

    @Autowired
    private IInventoryService inventoryService;

    @PostMapping("/save")
    public ResponseEntity<?>save(@RequestBody InventoryDTO inventoryDTO) throws URISyntaxException {
        if (inventoryDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        else {
            inventoryService.save(Inventory.builder()
                    .name(inventoryDTO.getName())
                    .description(inventoryDTO.getDescription())
                    .stock(inventoryDTO.getStock())
                    .price(inventoryDTO.getPrice())
                    .category(inventoryDTO.getCategory())
                    .provider(inventoryDTO.getProvider())
                    .FechaCreacion(inventoryDTO.getFechaCreacion())
                    .FechaActualizacion(inventoryDTO.getFechaActualizacion())
                    .build());
            return ResponseEntity.created(new URI("/api/crud/save")).build();
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?>findById(@PathVariable Long id){

        Optional<Inventory> inventoryOptional = inventoryService.findById(id);

        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            InventoryDTO inventoryDTO = InventoryDTO.builder()
                    .id(inventory.getId())
                    .name(inventory.getName())
                    .description(inventory.getDescription())
                    .stock(inventory.getStock())
                    .price(inventory.getPrice())
                    .category(inventory.getCategory())
                    .provider(inventory.getProvider())
                    .FechaCreacion(inventory.getFechaCreacion())
                    .FechaActualizacion(inventory.getFechaActualizacion())
                    .build();
            return ResponseEntity.ok(inventoryDTO);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?>findByName(@PathVariable String name) {
        Inventory inventory = inventoryService.findByName(name);
        if (inventory != null) {
            InventoryDTO inventoryDTO = InventoryDTO.builder()
                    .id(inventory.getId())
                    .name(inventory.getName())
                    .description(inventory.getDescription())
                    .stock(inventory.getStock())
                    .price(inventory.getPrice())
                    .category(inventory.getCategory())
                    .provider(inventory.getProvider())
                    .FechaCreacion(inventory.getFechaCreacion())
                    .FechaActualizacion(inventory.getFechaActualizacion())
                    .build();
            return ResponseEntity.ok(inventoryDTO);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?>findAll() {
        List<InventoryDTO> inventoryList = inventoryService.findAll()
                .stream()
                .map(inventory -> InventoryDTO.builder()
                        .id(inventory.getId())
                        .name(inventory.getName())
                        .description(inventory.getDescription())
                        .stock(inventory.getStock())
                        .price(inventory.getPrice())
                        .category(inventory.getCategory())
                        .provider(inventory.getProvider())
                        .FechaCreacion(inventory.getFechaCreacion())
                        .FechaActualizacion(inventory.getFechaActualizacion())
                        .build())
                .toList();
        return ResponseEntity.ok(inventoryList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>update(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) {

        Optional<Inventory> inventoryOptional = inventoryService.findById(id);

        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            inventory.setName(inventoryDTO.getName());
            inventory.setDescription(inventoryDTO.getDescription());
            inventory.setStock(inventoryDTO.getStock());
            inventory.setPrice(inventoryDTO.getPrice());
            inventory.setCategory(inventoryDTO.getCategory());
            inventory.setProvider(inventoryDTO.getProvider());

            // Mantener la fecha de creación original
            inventory.setFechaCreacion(inventoryOptional.get().getFechaCreacion());

            // Actualizar la fecha de actualización a la fecha actual
            inventory.setFechaActualizacion(new Date());

            inventoryService.save(inventory);
            return ResponseEntity.ok("Inventory updated");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteById(@PathVariable Long id) {
        if (id != null) {
            inventoryService.deleteById(id);
            return ResponseEntity.ok("Inventory deleted");
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

}