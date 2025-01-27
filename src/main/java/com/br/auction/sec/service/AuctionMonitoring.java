/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.auction.sec.service;

import com.br.auction.sec.db.ItemsDB;
import com.br.auction.sec.entity.Items;
import com.br.auction.sec.util.CryptoUtils;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public JsonObject returnItem(Boolean currentItem, Items item) throws Exception {
        JsonObject json = new JsonObject();

        if (currentItem) {
            item = itemsList.get(0);
        }
        json.addProperty("itemValue", String.valueOf(CryptoUtils.encryptSim(item.getValue())));
        json.addProperty("itemName", String.valueOf(CryptoUtils.encryptSim(item.getName())));
        json.addProperty("itemImage", String.valueOf(CryptoUtils.encryptSim(item.getImage())));

        return json;
    }

    public void startAuction() throws Exception {
        JsonObject json = new JsonObject();
        remainingTime = 20;
        System.out.println("Iniciando scheduler.");
        multicastService.sendMessage(returnItem(true, new Items()));
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            if (remainingTime > 0) {
                remainingTime--;
                System.out.println("Tempo restante: " + remainingTime + " segundos.");
                try {
                    json.addProperty("time", CryptoUtils.encryptSim(remainingTime.toString()));
                } catch (Exception ex) {
                    Logger.getLogger(AuctionMonitoring.class.getName()).log(Level.SEVERE, null, ex);
                }
                multicastService.sendMessage(json);
            } else {
                try {
                    endAuction();
                } catch (Exception ex) {
                    Logger.getLogger(AuctionMonitoring.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void endAuction() throws Exception {
        JsonObject json = new JsonObject();
        if (scheduler != null && !scheduler.isShutdown()) {
            json.addProperty("shutdown", CryptoUtils.encryptSim("true"));
            multicastService.sendMessage(json);
            scheduler.shutdown();
        }

        this.itemsList.remove(0);
        System.out.println(itemsList.toString());
        if (!itemsList.isEmpty()) {

            for (Items item : itemsList) {
                multicastService.sendMessage(returnItem(false, item));
                startAuction();
                break;
            }

        }

    }

}
