package com.example.store.service;

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


}
