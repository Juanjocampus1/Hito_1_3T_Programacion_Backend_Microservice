package com.empresa.InventoryReportsAPI.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformDTO {

    private Long id;
    private byte[] pdfContent;
    private String reportName;
    private String description;
}
