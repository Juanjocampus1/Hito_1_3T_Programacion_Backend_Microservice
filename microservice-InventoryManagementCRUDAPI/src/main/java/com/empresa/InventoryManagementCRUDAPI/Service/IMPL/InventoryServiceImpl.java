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
        // Verificar si ya existe un producto con el mismo nombre
        Inventory existingProduct = inventoryRepository.findByName(inventory.getName());
        if (existingProduct != null) {
            // Si ya existe, actualizar el producto existente
            existingProduct.setDescription(inventory.getDescription());
            existingProduct.setStock(inventory.getStock());
            existingProduct.setPrice(inventory.getPrice());
            // También puedes realizar otras actualizaciones necesarias
            inventoryRepository.save(existingProduct);
        }
        else {
            // Si no existe, guardar el nuevo producto
            if (inventory.getFechaCreacion() == null || inventory.getFechaActualizacion() == null) {
                inventory.setFechaCreacion(new Date());
                inventory.setFechaActualizacion(new Date());
            }
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public Inventory update(Inventory inventory) {
        // Actualizar el producto
        inventory.setFechaActualizacion(new Date());
        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }
}
