package com.example.store.service;

import com.example.store.model.Item;
import com.example.store.model.Order;
import com.example.store.model.Product;
import com.example.store.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


@Service
public class StoreService {

    private final MongoTemplate mongoTemplate;

    private final String storeCollectionName = "store";

    @Autowired
    public StoreService(MongoTemplate mongoTemplate) {

        this.mongoTemplate = mongoTemplate;
    }

    public Store add(Store store) throws DataAccessException {

        applyPromotion(store);

        populateRevenue(store);

        mongoTemplate.save(store, storeCollectionName);

        return findById(store.get_id());
    }


    public Store place(Store store) {
        mongoTemplate.save(store, storeCollectionName);
        return findById(store.get_id());
    }

    public Store findById(String id) {
        return mongoTemplate.findById(
                id, Store.class, storeCollectionName);
    }


    public void applyPromotion(Store store) {

        String productName;
        for (Product p : store.getProducts()) {
            for (Item i : p.getItems()) {
                productName = i.getName();
                int count = i.getOrders().size();
                for (Order o : i.getOrders()) {
                    if (productName.equalsIgnoreCase("socks") && o.getCount() > 12) {
                        o.setDiscount(.05);
                    } else if (count > 1) {
                        o.setDiscount(.05);
                    }
                }

            }
        }
    }


    private void populateRevenue(Store store) {

        for (Product p : store.getProducts()) {
            for (Item i : p.getItems()) {
                int count = i.getOrders().size();
                double buyingPrice = i.getPrice();
                for (Order o : i.getOrders()) {
                    double selling_price_after_discount = o.getSellingPrice() - o.getSellingPrice() * o.getDiscount();
                    double revenue = (selling_price_after_discount - buyingPrice) * o.getCount();
                    o.setRevenue(revenue);
                    ;
                }
            }
        }


    }


}









