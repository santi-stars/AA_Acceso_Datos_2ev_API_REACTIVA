package com.svalero.gestitaller2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "work_order")
@Table(name = "work_order")
public class WorkOrder {

    @Id
    private String id;
    @Field(name = "order_date")
    @NotNull
    private LocalDate orderDate;
    @Field
    @NotBlank
    private String description;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;

}





