/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.auction.sec.service;

import com.br.auction.sec.db.ItemsDB;
import com.br.auction.sec.entity.Items;
import com.google.gson.JsonObject;
import java.util.List;
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
    private ItemsDB itemsDB;
    private List<Items> itemsList;

    public AuctionMonitoring(MulticastService multicastService) {
        this.multicastService = multicastService;
        this.itemsDB = new ItemsDB();
        itemsList = itemsDB.getItems();
    }

    public JsonObject returnCurrentItem() {
        JsonObject json = new JsonObject();

        Items item = itemsList.get(0);
        json.addProperty("itemValue", item.getValue());
        json.addProperty("itemName", item.getName());

        return json;
    }

    public void startAuction() {
        JsonObject json = new JsonObject();
        remainingTime = 20;
        System.out.println("Iniciando scheduler.");
        multicastService.sendMessage(returnCurrentItem());
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            if (remainingTime > 0) {
                remainingTime--;
                System.out.println("Tempo restante: " + remainingTime + " segundos.");
                json.addProperty("time", remainingTime.toString());
                multicastService.sendMessage(json);
            } else {
                endAuction();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void endAuction() {
        if (scheduler != null && !scheduler.isShutdown()) {
            System.out.println("[SHUTDOWN]");
            scheduler.shutdown();
        }

        JsonObject json = new JsonObject();

        itemsList.remove(0);
        // findByRange das imagens
        System.out.println(itemsList.toString());
        if (!itemsList.isEmpty()) {

            for (Items item : itemsList) { // Itere sobre seus objetos Picture
                json.addProperty("itemValue", item.getValue());
                json.addProperty("itemName", item.getName());
                multicastService.sendMessage(json);
                startAuction();
                break;
            }

        }

    }

}
