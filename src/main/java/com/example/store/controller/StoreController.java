package com.example.store.controller;


import com.example.store.model.Store;
import com.example.store.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class StoreController {

    private final StoreService storeService;

    private final static Logger logger = LoggerFactory.getLogger(StoreController.class);


    public StoreController(StoreService storeService) {

        this.storeService = storeService;
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    @ResponseBody
    public Store add(@RequestBody Store store) {
        logger.info("post store");
        Store savedStore = storeService.add(store);
        return savedStore;
    }


}
