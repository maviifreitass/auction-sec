package com.br.auction.sec.service;

import com.br.auction.sec.db.UserDB;
import com.br.auction.sec.server.ServerStatic;
import com.br.auction.sec.util.CryptoUtils;
import com.br.auction.sec.util.KeyUtils;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import org.json.JSONObject;

public class UnicastServer {

    private MessageDigest messageDigest;

    public static void readRequest() throws Exception {
        int port = 12345;

        try ( ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor aguardando conexões na porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                try (
                         DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());  BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String cpf = in.readLine();
                    System.out.println("CPF recebido: " + cpf);

                    UserDB userDB = new UserDB();

                    String publicKeyText = userDB.findByCpfSystem(cpf.trim());
                    SecretKey secretKeyOriginal = ServerStatic.getSecretKey();
                    System.out.println("SECRET KEY: " + secretKeyOriginal.hashCode());
                    JSONObject sendMsg = new JSONObject();
                    System.out.println(publicKeyText);
                    PublicKey publicKey = KeyUtils.decodePublicKey(publicKeyText);

                    sendMsg.put("Port", "5000");

                    sendMsg.put("Group", "230.0.0.0");

                    byte[] iniVetor = ServerStatic.getIniVetor();
                    String iniVetorString = Base64.getEncoder().encodeToString(iniVetor);
                    System.out.println( Arrays.toString(iniVetor));
                    sendMsg.put("IV", iniVetorString);

                    byte[] encodedKey = secretKeyOriginal.getEncoded();
                    String symmetricKeyString = Base64.getEncoder().encodeToString(encodedKey);
                    sendMsg.put("Key", symmetricKeyString);

                    String encryptedMessage = encryptMessage(sendMsg.toString().getBytes(), publicKey);

                    System.out.println(encryptedMessage);
                    byte[] encryptedBytes = encryptedMessage.getBytes(StandardCharsets.UTF_8);

                    byte[] hashBytes = CryptoUtils.calculateHash(sendMsg.toString());
                    byte[] encryptedHashBytes = CryptoUtils.signHash(hashBytes);

                    out.writeInt(encryptedBytes.length);
                    out.write(encryptedBytes);

                    out.writeInt(encryptedHashBytes.length);
                    out.write(encryptedHashBytes);

                    out.flush();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    public static String encryptMessage(byte[] message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedMessage = null;
        encryptedMessage = cipher.doFinal(message);

        return Base64.getEncoder().encodeToString(encryptedMessage);
    }

}
