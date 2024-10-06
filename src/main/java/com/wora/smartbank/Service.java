package com.wora.smartbank;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Service {

    public Service() {
        System.out.println("service instancitated");
    }

    public String getMessage(){
        return "hey from service";
    }
}
