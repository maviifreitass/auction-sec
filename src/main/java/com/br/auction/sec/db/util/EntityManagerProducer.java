/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.auction.sec.db.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Classe responsável por produzir e fechar instâncias de EntityManager.
 * Esta classe é um bean CDI e é anotada com @ApplicationScoped para garantir
 * que seja criada apenas uma instância por aplicação.
 */
@ApplicationScoped
public class EntityManagerProducer {

    private EntityManagerFactory factory;

    public EntityManagerProducer() {
        factory = Persistence.createEntityManagerFactory("auction-systemPU");
    }

    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return factory.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager manager) {
        manager.close();
    }
}
