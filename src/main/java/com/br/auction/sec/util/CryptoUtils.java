package com.br.auction.sec.util;

import com.br.auction.sec.server.ServerStatic;
import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class CryptoUtils {

    public static String encryptWithPublicKey(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String signMessage(String message, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(message.getBytes());
        byte[] signedBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signedBytes);
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int ID_LENGTH = 12;

    public String generateRandomId() {
        SecureRandom random = new SecureRandom();
        StringBuilder id = new StringBuilder(ID_LENGTH);

        for (int i = 0; i < ID_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            id.append(CHARACTERS.charAt(index));
        }

        return id.toString();
    }

    public static String encryptSim(
            String plainText)
            throws Exception {
        Cipher cipher
                = Cipher.getInstance(
                        "AES/CBC/PKCS5Padding");

        IvParameterSpec ivParameterSpec
                = new IvParameterSpec(
                        ServerStatic.getIniVetor());

        cipher.init(Cipher.ENCRYPT_MODE,
                ServerStatic.getSecretKey(),
                ivParameterSpec);

        return java.util.Base64.getEncoder().encodeToString(cipher.doFinal(
            plainText.getBytes()));
    }

    public static String decryptSim(
            String cipherText,
            SecretKey secretKey,
            byte[] initializationVector)
            throws Exception {
        Cipher cipher
                = Cipher.getInstance(
                        "AES/CBC/PKCS5Padding");

        IvParameterSpec ivParameterSpec
                = new IvParameterSpec(
                        initializationVector);

        cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey,
                ivParameterSpec);
        
        byte[] byteText = java.util.Base64.getDecoder().decode(cipherText);
        byte[] result
                = cipher.doFinal(byteText);

        return new String(result);
    }

}
