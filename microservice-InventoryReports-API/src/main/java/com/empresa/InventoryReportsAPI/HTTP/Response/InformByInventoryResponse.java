package com.empresa.InventoryReportsAPI.HTTP.Response;

import com.empresa.InventoryReportsAPI.DTO.InventoryDTO;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class InformByInventoryResponse {

    private String InformTitle;
    private byte[] PDFContent;
    private List<InventoryDTO> inventories;

}
