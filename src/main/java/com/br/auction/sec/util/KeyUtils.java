package com.br.auction.sec.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {

    public static PublicKey decodePublicKey(String keyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
    }

    public static PrivateKey decodePrivateKey(String keyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
        keyGen.initialize(2048); 
        return keyGen.generateKeyPair();
    }

    public static String keyToBase64(java.security.Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
