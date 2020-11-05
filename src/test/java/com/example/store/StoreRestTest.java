package com.example.store;


import com.example.store.model.*;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
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

        items = new ArrayList<>();
        items.add(new Item("socks", 10L, LocalDate.of(2020, 6, 10), 100));
        products.add(new Product("Sports", items));


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

        String order_id = "123";

        String productName = "Catch Me You If you Can";

        Order order = new Order(order_id, productName, LocalDateTime.now(), 2, Status.ACTIVE, 40L);

        List<Order> orders = new ArrayList<>();


        orders.add(order);

        upsertOrder(store, orders, productName);


        ResponseEntity<Store> responseEntity = restTemplate.postForEntity(
                "/store", store, Store.class);
        Store savedStore = responseEntity.getBody();


        List<Order> orderList = getOrder(savedStore, productName);

        List<String> orderIdList = orderList.stream().map(x -> x.get_id()).collect(Collectors.toList());


        assertEquals(order_id, orderIdList.get(0));

    }

    @Test
    public void test_checkout_an_order() {

        mongoTemplate.save(store);

        Store store = mongoTemplate.findById("1234", Store.class, collectionName);

        String order_id = "123";

        String productName = "Catch Me You If you Can";

        Order order = new Order(order_id, productName, LocalDateTime.now(), 2, Status.CHECKEDOUT, 70L);

        List<Order> orders = new ArrayList<>();

        orders.add(order);

        upsertOrder(store, orders, productName);

        ResponseEntity<Store> responseEntity = restTemplate.postForEntity(
                "/store", store, Store.class);
        Store savedStore = responseEntity.getBody();

        List<Order> orderList = getOrder(savedStore, productName);

        List<Status> statusList = orderList.stream().map(x -> x.getStatus()).collect(Collectors.toList());


        assertEquals("CHECKEDOUT", statusList.get(0).name());
    }

    @Test
    public void test_checkout_based_on_rules() {

        mongoTemplate.save(store);

        Store store = mongoTemplate.findById("1234", Store.class, collectionName);

        String order_id = "212";
        List<Order> orders = new ArrayList<>();

        String productName = "socks";

        Order order = new Order(order_id, productName, LocalDateTime.now(), 20, Status.CHECKEDOUT, 80L);

        orders.add(order);

        upsertOrder(store, orders, productName);

        ResponseEntity<Store> responseEntity = restTemplate.postForEntity(
                "/store", store, Store.class);
        Store savedStore = responseEntity.getBody();


        List<Order> orderList = getOrder(savedStore, productName);

        List<Double> discountList = orderList.stream().map(x -> x.getDiscount()).collect(Collectors.toList());


        Double discount = .05d;

        assertEquals(discount, discountList.get(0));
    }

    @Test
    public void test_revenue_for_a_time_period() {

        mongoTemplate.save(store);

        Store store = mongoTemplate.findById("1234", Store.class, collectionName);

        String order_id = "212";
        List<Order> orders = new ArrayList<>();

        String productName = "socks";

        Order order = new Order(order_id, productName, LocalDateTime.now(), 20, Status.CHECKEDOUT, 80L);

        orders.add(order);

        upsertOrder(store, orders, productName);

        ResponseEntity<Store> responseEntity = restTemplate.postForEntity(
                "/store", store, Store.class);
        Store savedStore = responseEntity.getBody();


        List<Order> orderList = getOrder(savedStore, productName);

        List<Double> revinueList = orderList.stream().map(x -> x.getRevenue()).collect(Collectors.toList());

        Long buyingPrice = 10L;
        int count = 20;
        Long sellingPrice = 80L;
        Double discount = .05;

        Double selling_price_after_discount = sellingPrice - sellingPrice * discount;
        Double revenue = (selling_price_after_discount - buyingPrice) * count;

        assertEquals(revenue, revinueList.get(0));
    }


    private void upsertOrder(Store store, List<Order> orders, String productName) {
        for (Product p : store.getProducts()) {
            for (Item i : p.getItems()) {
                if (i.getName().equalsIgnoreCase(productName)) {
                    i.getOrders().addAll(orders);
                }
            }
        }
    }

    private List<Order> getOrder(Store savedStore, String productName) {

        List<Order> orderList = savedStore.getProducts()
                .stream()
                .flatMap(x -> x.getItems().stream())
                .filter(x -> x.getName().equalsIgnoreCase(productName))
                .flatMap(x -> x.getOrders().stream())
                .collect(Collectors.toList());

        return orderList;
    }


}
