package com.empresa.InventoryManagementCRUDAPI.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Builder
@Table(name = "inventory")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_producto")
    private String name;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "stock_disponible")
    private int stock;

    @Column(name = "precio")
    private BigDecimal price;

    @Column(name = "categoria")
    private String category;

    @Column(name = "proveedor")
    private String provider;

    @Column(name = "fecha_creacion")
    private Date FechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Date FechaActualizacion;

    @Column(name = "id_inform")
    private Long idInform;
}