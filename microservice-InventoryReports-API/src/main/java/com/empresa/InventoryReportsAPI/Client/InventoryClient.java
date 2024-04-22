package com.empresa.InventoryReportsAPI.Client;

import com.empresa.InventoryReportsAPI.DTO.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-InventoryManagementCRUDAPI", url = "localhost:8080/api/crud")
public interface InventoryClient {

    @GetMapping("/find-by-inform/{idInform}")
    List<InventoryDTO> findAllByIdInform (@PathVariable Long idInform);
}
