package com.example.store.model;

import lombok.*;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Order {
    @NonNull
    int count;
    @NonNull
    private String _id;
    @NonNull
    private String name;
    @NonNull
    private LocalDateTime dateOfOrder;
    @NonNull
    private Status status;
    @NonNull
    private Long sellingPrice;

    private Double revenue;
    private Double discount = 1d;
}
