package com.example.store;

import com.example.store.repositoty.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class StoreApplication {

	@Autowired
	StoreRepository storeRepository;

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}



	@Component
	class Runner{
		@EventListener(ApplicationReadyEvent.class)
        public void intitializForums() throws Exception {
            storeRepository.deleteAll();
        }

	}

}
