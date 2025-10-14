package com.vidaplus.sghss_api.config.crypto;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CpfConverter implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    @Value("${app.encryption.key}")
    private String secretKey;


    @Override
    public String convertToDatabaseColumn(String cpf) {
        if (cpf == null) {
            return null;
        }
        try {
            Key key = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedCpf = cipher.doFinal(cpf.getBytes());
            return Base64.getEncoder().encodeToString(encryptedCpf);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar o CPF", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String encryptedCpf) {
        if (encryptedCpf == null) {
            return null;
        }
        try {
            Key key = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedCpf = Base64.getDecoder().decode(encryptedCpf);
            return new String(cipher.doFinal(decodedCpf));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar o CPF", e);
        }
    }
}