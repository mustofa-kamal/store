package services;

import com.example.store.model.Store;
import com.example.store.repositoty.StoreRepository;

public class StoreService {
    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store saveStore() {

        Store store = new Store();



        return store;
    }

}
