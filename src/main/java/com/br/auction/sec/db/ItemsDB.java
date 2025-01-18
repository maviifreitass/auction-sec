/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.auction.sec.db;

import com.br.auction.sec.db.util.PostgresWrapper;
import com.br.auction.sec.entity.Items;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de repositório do banco de dados que irá realizar trazes os dados dos
 * usuários ! Autenticação e autorização serão implementadas em outra classe !
 */
@Named
@ApplicationScoped
public class ItemsDB implements Serializable {

    @Inject
    private PostgresWrapper pw;

    @Inject
    private EntityManager em;

    /**
     * Busca um usuário pelo ID.
     *
     * @param id O ID do usuário a ser buscado.
     * @return O usuário encontrado, ou null se não encontrado.
     */
    public Items findById(Long id) {
        return em.find(Items.class, id);
    }

    /**
     * Retorna uma lista de todos os usuários.
     *
     * @return Uma lista contendo todos os usuários.
     */
    public List<Items> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Items> criteriaQuery = criteriaBuilder.createQuery(Items.class);
        Root<Items> root = criteriaQuery.from(Items.class);
        criteriaQuery.select(root);

        return em.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Salva o usuário.
     *
     * @param user O usuário a ser salvo.
     */
    public void save(Items user) {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Items> getItems() {
        String sql = "SELECT id, name, value, image FROM items";
        List<Items> itemsList = new ArrayList<>();

        PostgresWrapper pw = new PostgresWrapper();
        pw.openPostgresConnection();

        try {
            try ( Connection connection = pw.getConnection()) {
                try ( PreparedStatement statement = connection.prepareStatement(sql)) {
                    try ( ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            Items item = new Items();
                            item.setId(resultSet.getLong("id"));
                            item.setName(resultSet.getString("name"));
                            item.setValue(resultSet.getString("value"));
                            item.setImage(resultSet.getString("image"));
                            itemsList.add(item);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    pw.closeConnection();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemsList;
    }

    public void createItem(Items item) {
        String sql = "INSERT INTO items (name, value, image) VALUES (?, ?, ?);";

        PostgresWrapper pw = new PostgresWrapper();
        pw.openPostgresConnection();

        try {
            try ( Connection connection = pw.getConnection()) {
                try ( PreparedStatement statement = connection.prepareStatement(sql)) {
                    // Definindo os valores para a query
                    statement.setString(1, item.getName());
                    statement.setString(2, item.getValue());
                    statement.setString(3, item.getImage());

                    // Executando a query para inserir o item
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    pw.closeConnection();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
