package com.example.store.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String name;
    private Long price;
    private LocalDate dateOfArrival;
    private Integer count;

}
