package com.vidaplus.sghss_api.config.crypto;

import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
@Component
public class CpfConverter implements AttributeConverter<String, String> {

	private static final Logger logger = LoggerFactory.getLogger(CpfConverter.class);
	private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

	private static String secretKey;

	@Value("${app.encryption.key}")
	private String injectedSecretKey;

	@PostConstruct
	public void init() {
		if (injectedSecretKey == null || injectedSecretKey.length() != 32) {
			logger.error(
					"A chave de criptografia 'app.encryption.key' não foi definida corretamente ou não tem 32 caracteres.");
		}
		secretKey = injectedSecretKey;
		logger.info("Chave de criptografia carregada com sucesso no CpfConverter.");
	}

	@Override
	public String convertToDatabaseColumn(String cpf) {
		if (cpf == null || secretKey == null) {
			return null;
		}
		try {
			Key key = new SecretKeySpec(secretKey.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedCpf = cipher.doFinal(cpf.getBytes());
			return Base64.getEncoder().encodeToString(encryptedCpf);
		} catch (Exception e) {
			logger.error("Erro ao criptografar o CPF: {}", e.getMessage(), e);
			throw new RuntimeException("Erro ao criptografar o CPF", e);
		}
	}

	@Override
	public String convertToEntityAttribute(String encryptedCpf) {
		if (encryptedCpf == null || secretKey == null) {
			return null;
		}
		try {
			Key key = new SecretKeySpec(secretKey.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decodedCpf = Base64.getDecoder().decode(encryptedCpf);
			return new String(cipher.doFinal(decodedCpf));
		} catch (Exception e) {
			logger.error("Erro ao descriptografar o CPF: {}", e.getMessage(), e);
			throw new RuntimeException("Erro ao descriptografar o CPF", e);
		}
	}
}