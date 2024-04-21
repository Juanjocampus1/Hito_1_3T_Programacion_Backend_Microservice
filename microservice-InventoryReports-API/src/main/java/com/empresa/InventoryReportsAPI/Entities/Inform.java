package com.empresa.InventoryReportsAPI.Entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@Table(name = "Informe")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Inform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "PDF")
    private byte[] pdfContent;

    @Column(name = "nombre_PDF")
    private String reportName;

    @Column(name = "descripcion_PDF")
    private String description;

}
