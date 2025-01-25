/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.auction.sec.server;

import com.br.auction.sec.util.KeyUtils;
import jakarta.enterprise.context.ApplicationScoped;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;

/**
 *
 * @author maria
 */

@ApplicationScoped
public class ServerStatic {

    private static SecretKey secretKey;

    static {
        try {
            secretKey = KeyUtils.generateSecret();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServerStatic.class.getName()).log(Level.SEVERE, "Failed to generate secret key", ex);
        }
    }

    protected ServerStatic() {
    }

    public static SecretKey getSecretKey() {
        return secretKey;
    }

}
