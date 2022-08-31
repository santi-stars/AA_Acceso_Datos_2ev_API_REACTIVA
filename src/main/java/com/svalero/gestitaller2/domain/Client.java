package com.svalero.gestitaller2.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "client")
public class Client {

    @Id
    private String id;
    @Field
    @NotNull
    private String name;
    @Field
    @NotNull
    private String surname;
    @Field
    @NotNull
    @Pattern(regexp = "[0-9]{8}[A-Z]")
    private String dni;
    @Field(name = "vip_client")
    private boolean vip;
    @Field
    @NotNull
    private float latitude;
    @Field
    @NotNull
    private float longitude;
    @Field
    @Lob
    private byte[] clientImage;
    @OneToMany(mappedBy = "client")
    @JsonBackReference(value = "client-bike")
    private List<Bike> bikes;
    @OneToMany(mappedBy = "client")
    @JsonBackReference(value = "client-work_order")
    private List<WorkOrder> workOrders;
}
