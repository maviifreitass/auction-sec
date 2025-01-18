package com.br.auction.sec.util;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyUtils {
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }
}
