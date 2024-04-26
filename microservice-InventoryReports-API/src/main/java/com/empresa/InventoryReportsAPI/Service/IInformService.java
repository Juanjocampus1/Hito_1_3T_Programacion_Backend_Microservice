package com.empresa.InventoryReportsAPI.Service;

import com.empresa.InventoryReportsAPI.Entities.Inform;
import com.empresa.InventoryReportsAPI.HTTP.Response.InventoryByInformResponse;

import java.util.List;
import java.util.Optional;

public interface IInformService {

    List<Inform> findAll();
    Optional<Inform> findById(Long id);
    Inform save(Inform inventory);
    void deleteById(Long id);
    Inform generateAndSaveReport(String reportTitle, String content);

}
