/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.auction.sec.server;

import com.br.auction.sec.util.KeyUtils;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
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
    private static PrivateKey privateKey;
    private static PublicKey publicKey;
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
            privateKey = createPrivateKey();
            createPublicKey();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServerStatic.class.getName()).log(Level.SEVERE, "Failed to generate secret key", ex);
        } catch (Exception ex) {
            Logger.getLogger(ServerStatic.class.getName()).log(Level.SEVERE, null, ex);
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

    public static PrivateKey getPrivateKey() {
        return privateKey;
    }

    public static PublicKey getPublicKey() {
        return publicKey;
    }

    public static byte[] createInitializationVector() {

        byte[] initializationVector
                = new byte[16];
        SecureRandom secureRandom
                = new SecureRandom();
        secureRandom.nextBytes(initializationVector);
        return initializationVector;
    }

    public static PrivateKey createPrivateKey() throws Exception {

        KeyPair keyPair = KeyUtils.generateKeyPair();

        return keyPair.getPrivate();
    }

    public static void createPublicKey() throws Exception {

        KeyPair keyPair = KeyUtils.generateKeyPair();

        savePublicKey(keyPair.getPublic());
    }

    public static void savePublicKey(PublicKey publicKey) throws IOException {
        String encodedKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\maria\\Documents\\NetBeansProjects\\publicKey.txt"))) {
            writer.write(encodedKey);
        }
    }

}
