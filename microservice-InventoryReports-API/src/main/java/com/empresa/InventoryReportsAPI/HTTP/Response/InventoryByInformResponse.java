package com.empresa.InventoryReportsAPI.HTTP.Response;

import com.empresa.InventoryReportsAPI.DTO.InventoryDTO;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryByInformResponse {

    private String ReportTitle;
    private byte[] PDFContent;
    private List<InventoryDTO> inventories;

}
