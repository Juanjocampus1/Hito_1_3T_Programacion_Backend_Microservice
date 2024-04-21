package com.empresa.InventoryManagementCRUDAPI.Service;

import com.empresa.InventoryManagementCRUDAPI.Entities.Inventory;

import java.util.List;
import java.util.Optional;

public interface IInventoryService {

    public List<Inventory> findAll();
    public Optional<Inventory> findById(Long id);
    public Inventory findByName(String name);
    public Inventory save(Inventory inventory);
    public Inventory update(Inventory inventory);
    public List<Inventory> findAllByIdInform(Long IdInform);
    public void deleteById(Long id);

}
