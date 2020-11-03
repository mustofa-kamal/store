package com.example.store;


import com.example.store.model.Item;
import com.example.store.model.Product;
import com.example.store.model.Store;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration //mongoDB
public class StoreRestTest {

    String collectionName;

    Store store;


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Before
    public void before() {

        collectionName = "store";
        store = new Store();
        store.set_id("1234");
        store.setName("Simple Store");



        List<Item> items = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        items.add(new Item("Specialized", 16L, LocalDate.of(2020, 6, 30), 20));
        items.add(new Item("Phonix", 16L, LocalDate.of(2020, 6, 10), 10));
        products.add(new Product("BiCycle", items));

        items = new ArrayList<>();
        items.add(new Item("Catch Me You If you Can", 16L, LocalDate.of(2020, 6, 30), 20));
        products.add(new Product("Book", items));

        items = new ArrayList<>();
        items.add(new Item("Helicopter", 16L, LocalDate.of(2020, 6, 10), 10));
        products.add(new Product("Toy", items));


        store.setProducts(products);

    }

    @Test
    public void test_add_store() {

        ResponseEntity<Store> responseEntity = restTemplate.postForEntity(
                "/store", store, Store.class);
        Store savedStore = responseEntity.getBody();

        assertNotNull("Should have an PK", savedStore.get_id());
    }

    @Test
    public void test_place_an_order() {

        mongoTemplate.save(store);


        Store store = mongoTemplate.findById("1234", Store.class, collectionName);






        int tt=0;

    }


}
