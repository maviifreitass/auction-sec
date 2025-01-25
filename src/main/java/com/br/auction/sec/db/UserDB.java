/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.auction.sec.db;

import com.br.auction.sec.db.util.PostgresWrapper;
import com.br.auction.sec.db.util.PostgresWrapperClient;
import com.br.auction.sec.entity.User;
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
import java.util.List;

/**
 * Classe de repositório do banco de dados que irá realizar trazes os dados dos
 * usuários ! Autenticação e autorização serão implementadas em outra classe !
 */
@Named
@ApplicationScoped
public class UserDB implements Serializable {

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
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    /**
     * Retorna uma lista de todos os usuários.
     *
     * @return Uma lista contendo todos os usuários.
     */
    public List<User> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        return em.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Salva o usuário.
     *
     * @param user O usuário a ser salvo.
     */
    public void save(User user) {
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

    public void createUser(User user) {
        String sql = "INSERT INTO users (cpf, public_key) VALUES (?, ?);";

        PostgresWrapper pw = new PostgresWrapper();
        pw.openPostgresConnection();

        try {
            try ( Connection connection = pw.getConnection()) {
                try ( PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, user.getCpf());
                    statement.setString(2, user.getPublicKey());

                    // Executando o comando SQL
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                pw.closeConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.createUserPrivacy(user);
    }

    public String findByCpfSystem(String cpf) {
        String sql = "SELECT public_key FROM users where cpf = '" + cpf + "' ";

        PostgresWrapper pw = new PostgresWrapper();
        pw.openPostgresConnection();

        String publicKey = null;

        try {
            try ( Connection connection = pw.getConnection()) {
                try ( PreparedStatement statement = connection.prepareStatement(sql)) {
                    try ( ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            publicKey = (resultSet.getString("public_key"));
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
            return null;
        }

        return publicKey;
    }

    // --------------------- Client Database --------------------------
    public void createUserPrivacy(User user) {
        String sql = "INSERT INTO users_privacy (cpf, private_key) VALUES (?, ?);";

        PostgresWrapperClient pw = new PostgresWrapperClient();
        pw.openPostgresConnection();

        try {
            try ( Connection connection = pw.getConnection()) {
                try ( PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, user.getCpf());
                    statement.setString(2, user.getPrivateKey());

                    // Executando o comando SQL
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                pw.closeConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User findByCpf(String cpf, Boolean isServer) {
        String sql = "SELECT id, cpf, private_key FROM users_privacy where cpf = '" + cpf + "' ";
        User user = new User();

        PostgresWrapperClient pw = new PostgresWrapperClient();
        pw.openPostgresConnection();

        try {
            try ( Connection connection = pw.getConnection()) {
                try ( PreparedStatement statement = connection.prepareStatement(sql)) {
                    try ( ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            user.setId(resultSet.getLong("id"));
                            user.setCpf(resultSet.getString("cpf"));
                            user.setPrivateKey(resultSet.getString("private_key"));
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
            return null;
        }

        return user;
    }

    // -----------------------------------------------------------------------------------
}
