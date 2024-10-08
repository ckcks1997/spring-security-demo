package site.companycolor.demo;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JasyptConfigTest {

    private static final String SECRET_KEY = ""; // 암호화 키

    @Test
    void string_encryption() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(SECRET_KEY);
        config.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        config.setIvGenerator(new RandomIvGenerator());
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        String originalString = ""; //암호화 할 string

        // 암호화
        String encryptedString = encryptor.encrypt(originalString);
        System.out.println("Encrypted String ::: ENC(" + encryptedString + ")");

        // 복호화
        String decryptedString = encryptor.decrypt(encryptedString);
        System.out.println("Decrypted String ::: " + decryptedString);

        assertEquals(originalString, decryptedString);
    }
}