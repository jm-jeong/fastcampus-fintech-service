//package com.fastcampus.fintechservice.config;
//
//import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
//import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
//import org.jasypt.iv.RandomIvGenerator;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class JasyptConfigTest {
//
//    //1. SECRET_KEY에 패스워드 입력
//    //2. originalString에 암호화할 문자열 입력
//    private String SECRET_KEY = "";
//
//    @Test
//    void string_encryption() {
//        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
//        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//        config.setPassword(SECRET_KEY);
//        config.setAlgorithm("PBEWithHMACSHA512AndAES_256");
//        config.setIvGenerator(new RandomIvGenerator());
//        config.setKeyObtentionIterations("1000");
//        config.setPoolSize("1");
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
//        config.setStringOutputType("base64");
//        encryptor.setConfig(config);
//
//        String originalString = "";
//
//
//        // 암호화
//        String encryptedString = encryptor.encrypt(originalString);
//        System.out.println("Encrypted String ::: ENC(" + encryptedString + ")");
//
//        // 복호화
//        String decryptedString = encryptor.decrypt(encryptedString);
//        System.out.println("Decrypted String ::: " + decryptedString);
//
//        assertEquals(originalString, decryptedString);
//    }
//    //    client-id: fcfd9578acfc6d829c5622c36337d715
//    //    client-secret: 66DxS4d59baYvZy7vlBL9vvf3vhmnaZ2
//}