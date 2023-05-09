package com.avs.socksshopbe.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Socks {
    public Socks(String color, Byte cottonPart) {
        this.color = color;
        this.cottonPart = cottonPart;
        this.quantity = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "color")
    private String color;

    @Column(name = "cotton_part")
    private Byte cottonPart;

    @Column(name = "quantity")
    private Integer quantity;
}
