package com.example.store.model;


import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Item {
    List<Order> orders = new ArrayList<>();
    @NonNull
    private String name;
    @NonNull
    private Long price;
    @NonNull
    private LocalDate dateOfArrival;
    @NonNull
    private Integer count;

}
