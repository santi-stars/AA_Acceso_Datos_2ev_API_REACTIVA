package com.svalero.gestitaller2.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BikeDTO {

    private String brand;
    private String model;
    private String licensePlate;
    private byte[] bikeImage;
    private long client;
}
