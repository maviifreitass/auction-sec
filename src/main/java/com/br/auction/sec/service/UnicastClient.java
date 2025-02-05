package com.br.auction.sec.service;

import com.br.auction.sec.client.ClientMainPanel;
import com.br.auction.sec.entity.User;
import com.br.auction.sec.util.CryptoUtils;
import com.br.auction.sec.util.KeyUtils;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class UnicastClient {

    public static String sendRequest(User user) throws Exception {
        String serverAddress = "127.0.0.1";
        int port = 12345;
        String decryptedMessage = null;

        try ( Socket socket = new Socket(serverAddress, port);  DataInputStream in = new DataInputStream(socket.getInputStream());) {

            System.out.println("Conectado ao servidor em " + serverAddress + ":" + port);

            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            out.println(user.getCpf());

            int encSize = in.readInt();
            byte[] encBytes = new byte[encSize];
            in.readFully(encBytes);

            int hashLength = in.readInt();
            byte[] hashBytes = new byte[hashLength];
            in.readFully(hashBytes);

            String encryptedResponse = new String(encBytes, StandardCharsets.UTF_8);
            PrivateKey privateKey = KeyUtils.decodePrivateKey(user.getPrivateKey());
            decryptedMessage = decryptAssim(encryptedResponse, privateKey);

            if (CryptoUtils.checkDecrypt(decryptedMessage, hashBytes, readPublicKey())) {
                return decryptedMessage;
            }
            
        } catch (IOException e) {
            System.err.println("Erro no cliente: " + e.getMessage());
            return null;
        }
        
        return decryptedMessage;
    }


    public static PublicKey readPublicKey() throws Exception {
        String keyPath = "C:\\Users\\maria\\Documents\\NetBeansProjects\\publicKey.txt";
        String encodedKey = new String(Files.readAllBytes(Paths.get(keyPath))).trim();
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    public static String decryptAssim(String encryptedMessage, PrivateKey privateKey) {
        try {
            PrivateKey clientPrK = privateKey;
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, clientPrK);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));

            return new String(decryptedBytes);
        } catch (IllegalBlockSizeException | BadPaddingException
                | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException ex) {
            Logger.getLogger(ClientMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
