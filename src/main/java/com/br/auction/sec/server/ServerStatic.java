/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.auction.sec.server;

import jakarta.enterprise.context.ApplicationScoped;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author maria
 */
@ApplicationScoped
public class ServerStatic {

    private static SecretKey secretKey;
    private static byte[] iniVetor;

    static {
        try {
            SecureRandom securerandom
                    = new SecureRandom();

            KeyGenerator keygenerator
                    = KeyGenerator.getInstance("AES");

            keygenerator.init(256, securerandom);
            secretKey = keygenerator.generateKey();
            iniVetor = createInitializationVector();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServerStatic.class.getName()).log(Level.SEVERE, "Failed to generate secret key", ex);
        }
    }

    protected ServerStatic() {
    }

    public static SecretKey getSecretKey() {
        return secretKey;
    }

    public static byte[] getIniVetor() {
        return iniVetor;
    }

    public static byte[] createInitializationVector() {

        byte[] initializationVector
                = new byte[16];
        SecureRandom secureRandom
                = new SecureRandom();
        secureRandom.nextBytes(initializationVector);
        return initializationVector;
    }

}
