package com.empresa.InventoryReportsAPI.Client;

import com.empresa.InventoryReportsAPI.DTO.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "msvc-InventoryManagementCRUDAPI", url = "13.48.29.69:8081/api/crud")
public interface InventoryClient {

    @GetMapping("/findAll")
    List<InventoryDTO> findAll();
}
