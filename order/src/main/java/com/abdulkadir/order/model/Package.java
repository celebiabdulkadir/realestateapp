package com.abdulkadir.order.model;

import jakarta.persistence.*;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "/package/") // Quotes the table name to allow the use of a reserved keyword
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Number price;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private Number duration;

    @Column(name = "advert_quantity")
    private Number advertQuantity;
    // Add constructor for DataLoader usage
    public Package(String name, Double price, String description, Integer duration, Integer advertQuantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.duration = duration;
        this.advertQuantity = advertQuantity;
    }




}

