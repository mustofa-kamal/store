package com.example.store;

import com.example.store.model.Item;
import com.example.store.model.Product;
import com.example.store.model.Store;
import com.example.store.repositoty.StoreRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)
@DataMongoTest
public class StoreServiceTest {
    String collectionName;

    Store store;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Before
    public void before() {

        collectionName = "store";
        Store s = new Store();
        s.set_id("1234");
        s.setName("Simple Store");


        List<Item> items=new ArrayList<>();
        List<Product> products = new ArrayList<>();

        items.add(new Item("Specialized",16L, LocalDate.of(2020, 6, 30),20));
        items.add(new Item("Phonix",16L, LocalDate.of(2020, 6, 10),10));
        products.add(new Product("BiCycle", items));

        items=new ArrayList<>();
        items.add(new Item("Catch Me You If you Can",16L, LocalDate.of(2020, 6, 30),20));
        products.add(new Product("Book", items));

        items=new ArrayList<>();
        items.add(new Item("Helicopter",16L, LocalDate.of(2020, 6, 10),10));
        products.add(new Product("Toy", items));


        s.setProducts(products);
        storeRepository.save(s);

    }

    @After
    public void after() {
        mongoTemplate.dropCollection(collectionName);
    }

    @Test
    public void checkStoreIdCreate() {
        assertNotNull(storeRepository);
        Store s = new Store();
        Store savedStore = storeRepository.save(s);
        assertNotNull(savedStore.get_id());
    }


    @Test
    public void test_list_of_items() {

        assertNotNull(storeRepository);

        Optional<Store> savedStore = storeRepository.findById("1234");

        storeRepository.findById("1234").ifPresent(s -> {

            List<String> expectedList = s.getProducts().stream()
                    .flatMap(p -> p.getItems().stream())
                    .map(Item::getName)
                    .collect(Collectors.toList());

            List<String> originalList = Arrays.asList("Specialized","Phonix","Catch Me You If you Can","Helicopter");

            assertEquals(originalList,expectedList);
        });

    }

    @Test
    public void test_place_an_order() {

        assertNotNull(storeRepository);

        Optional<Store> savedStore = storeRepository.findById("1234");

        storeRepository.findById("1234").ifPresent(s -> {

            List<String> expectedList = s.getProducts().stream()
                    .flatMap(p -> p.getItems().stream())
                    .map(Item::getName)
                    .collect(Collectors.toList());

            List<String> originalList = Arrays.asList("Specialized","Phonix","Catch Me You If you Can","Helicopter");

            assertEquals(originalList,expectedList);
        });

    }




}
