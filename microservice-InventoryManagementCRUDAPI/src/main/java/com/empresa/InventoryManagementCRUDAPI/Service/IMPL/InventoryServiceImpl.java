package com.empresa.InventoryManagementCRUDAPI.Service.IMPL;

import com.empresa.InventoryManagementCRUDAPI.Entities.Inventory;
import com.empresa.InventoryManagementCRUDAPI.Repository.InventoryRepository;
import com.empresa.InventoryManagementCRUDAPI.Service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements IInventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> findAll() {
        return (List<Inventory>) inventoryRepository.findAll();
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public Inventory findByName(String name) {
        return inventoryRepository.findByName(name);
    }

    @Override
    public void save(Inventory inventory) {
            // Si no existe, guardar el nuevo producto
            if (inventory.getFechaCreacion() == null ) {
                inventory.setFechaCreacion(new Date());
            }
            if (inventory.getFechaActualizacion() == null){
                inventory.setFechaActualizacion(new Date());
            }
            inventoryRepository.save(inventory);
    }

    @Override
    public Inventory update(Inventory inventory) {
        // Obtener el producto existente
        // Obtener el producto existente
        Inventory existingProduct = inventoryRepository
                .findById(inventory.getId())
                .orElseThrow(() -> new RuntimeException("No se encontró el producto con el id: " + inventory.getId()));

        // Mantener la fecha de creación original
        inventory.setFechaCreacion(existingProduct.getFechaCreacion());

        // Actualizar la fecha de actualización a la fecha actual
        inventory.setFechaActualizacion(new Date());

        // Actualizar el resto de los campos
        inventory.setName(inventory.getName());
        inventory.setDescription(inventory.getDescription());
        inventory.setStock(inventory.getStock());
        inventory.setPrice(inventory.getPrice());
        inventory.setCategory(inventory.getCategory());
        inventory.setProvider(inventory.getProvider());

        // Llamar al método save con la fecha de creación del producto existente
        save(inventory);

        return inventory;
    }

    @Override
    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }
}
