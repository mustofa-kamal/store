package com.example.store.controller;


import com.example.store.model.Store;
import com.example.store.service.StoreService;
import org.springframework.web.bind.annotation.*;

@RestController
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {

        this.storeService = storeService;
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    @ResponseBody
    public Store add(@RequestBody Store store) {
        Store s = storeService.add(store);
        return s;
    }

    @RequestMapping(value = "/place", method = RequestMethod.POST)
    @ResponseBody
    public Store placeAnOrder(@RequestBody Store store) {
        Store s = storeService.place(store);
        return s;
    }


}
