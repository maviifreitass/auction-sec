package com.br.auction.sec.util;

import com.br.auction.sec.server.ServerStatic;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class CryptoUtils {

    private static MessageDigest md;

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
        
        System.out.println(cipherText);
        

        cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey,
                ivParameterSpec);

        byte[] byteText = java.util.Base64.getDecoder().decode(cipherText);
        byte[] result
                = cipher.doFinal(byteText);

        return new String(result);
    }

    public static byte[] calculateHash(String message) throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(message.getBytes(StandardCharsets.UTF_8));
        return hashBytes;
    }

    public static byte[] signHash(byte[] hashBytes) {
        try {
            PrivateKey serverPk = ServerStatic.getPrivateKey();
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(serverPk);
            signature.update(hashBytes);
            return signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            Logger.getLogger(CryptoUtils.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public static boolean checkDecrypt(String decryptedMessage, byte[] hashEncrypted, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        md = MessageDigest.getInstance("SHA-256");
        byte[] messageHash = md.digest(decryptedMessage.getBytes(StandardCharsets.UTF_8));

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(messageHash);
        return signature.verify(hashEncrypted);

    }

}
