/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.auction.sec.service;

import com.google.gson.JsonObject;
import jakarta.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author maria
 */
public class AuctionMonitoring {

    private MulticastService multicastService;

    private static ScheduledExecutorService scheduler;
    private Integer remainingTime;

    public AuctionMonitoring(MulticastService multicastService) {
        this.multicastService = multicastService;
    }
    
    public JsonObject returnCurrentItem(){
        JsonObject json = new JsonObject();
        json.addProperty("itemValue", "5000");
        json.addProperty("itemName", "Macaco");
        
        return json;
    }

    public void startAuction() {
        JsonObject json = new JsonObject();
        remainingTime = 90;
        System.out.println("Iniciando scheduler.");
        multicastService.sendMessage(returnCurrentItem());
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            if (remainingTime > 0) {
                remainingTime--;
                System.out.println("Tempo restante: " + remainingTime + " segundos.");
                json.addProperty("time", remainingTime.toString());
                multicastService.sendMessage(json);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

}
