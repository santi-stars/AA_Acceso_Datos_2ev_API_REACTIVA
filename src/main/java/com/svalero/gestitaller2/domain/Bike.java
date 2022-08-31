package com.svalero.gestitaller2.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "bike")
public class Bike {

    @Id
    private String id;
    @Field
    private String brand;
    @Field
    @NotBlank
    private String model;
    @Field
    @NotBlank
    private String licensePlate;
    @Field
    @Lob
    private byte[] bikeImage;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToMany(mappedBy = "bike")
    @JsonBackReference(value = "bike-work_order")
    private List<WorkOrder> workOrders;

}
