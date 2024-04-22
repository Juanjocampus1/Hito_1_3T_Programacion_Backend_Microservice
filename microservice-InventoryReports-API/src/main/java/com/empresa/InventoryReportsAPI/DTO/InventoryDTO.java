package com.empresa.InventoryReportsAPI.DTO;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class InventoryDTO {

    private Long id;
    private String name;
    private String description;
    private int stock;
    private BigDecimal price;
    private String category;
    private String provider;
    private Date FechaCreacion;
    private Date FechaActualizacion;
    private Long idInform;
}
