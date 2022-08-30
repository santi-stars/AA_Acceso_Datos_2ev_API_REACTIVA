package com.svalero.gestitaller2.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class WorkOrderDTO {

    private LocalDate orderDate;
    private String description;
    public long client;
    private long bike;
}
