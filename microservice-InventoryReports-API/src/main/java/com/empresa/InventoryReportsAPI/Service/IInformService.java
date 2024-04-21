package com.empresa.InventoryReportsAPI.Service;

import com.empresa.InventoryReportsAPI.Entities.Inform;
import com.empresa.InventoryReportsAPI.HTTP.Response.InformByInventoryResponse;

import java.util.List;
import java.util.Optional;

public interface IInformService {

    public List<Inform> findAll();
    public Optional<Inform> findById(Long id);
    public Inform save(Inform inventory);
    public void deleteById(Long id);
    InformByInventoryResponse findInformByInventory(Long idInform);
}
