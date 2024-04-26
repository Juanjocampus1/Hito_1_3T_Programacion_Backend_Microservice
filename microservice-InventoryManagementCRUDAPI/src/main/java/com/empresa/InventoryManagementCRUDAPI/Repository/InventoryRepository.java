package com.empresa.InventoryManagementCRUDAPI.Repository;

import com.empresa.InventoryManagementCRUDAPI.Entities.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long>{
    Inventory findByName(String name);

}
