/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.auction.sec.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.security.PrivateKey;
import javax.crypto.SecretKey;

/**
 *
 * @author maria
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "public_key")
    private String publicKey;

    @Transient
    private String privateKey;

    @Transient
    private String idAuction;

    @Transient
    private SecretKey simetricKey;

    @Transient
    private byte[] iniVetor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(String idAuction) {
        this.idAuction = idAuction;
    }

    public SecretKey getSimetricKey() {
        return simetricKey;
    }

    public void setSimetricKey(SecretKey simetricKey) {
        this.simetricKey = simetricKey;
    }

    public byte[] getIniVetor() {
        return iniVetor;
    }

    public void setIniVetor(byte[] iniVetor) {
        this.iniVetor = iniVetor;
    }

    @Override
    public String toString() {
        return new com.google.gson.Gson().toJson(this);
    }

}
