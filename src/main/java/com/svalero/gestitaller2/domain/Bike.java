package com.svalero.gestitaller2.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "bike")
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PositiveOrZero
    private long id;
    @Column
    @NotBlank
    private String brand;
    @Column
    @NotBlank
    private String model;
    @Column
    @NotBlank
    private String licensePlate;
    @Column
    @Lob
    private byte[] bikeImage;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToMany(mappedBy = "bike")
    @JsonBackReference(value = "bike-work_order")
    private List<WorkOrder> workOrders;

}
