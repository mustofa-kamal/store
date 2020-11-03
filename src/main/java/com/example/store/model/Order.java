package com.example.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Order {
    @Id
    private String _id;
    private String name;
    private List<Item> items;

    public Order(String name, List<Item> items) {
        this.name=name;
        this.items = items;
    }
}
