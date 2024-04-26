package com.empresa.InventoryManagementCRUDAPI.Service;

import com.empresa.InventoryManagementCRUDAPI.Entities.Inventory;

import java.util.List;
import java.util.Optional;

public interface IInventoryService {

    List<Inventory> findAll();
    Optional<Inventory> findById(Long id);
    Inventory findByName(String name);
    void save(Inventory inventory);
    Inventory update(Inventory inventory);
    void deleteById(Long id);
}
