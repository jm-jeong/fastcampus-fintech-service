package com.fastcampus.fintechservice.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

// @Configuration
// @EnableEncryptableProperties
public class JasyptConfig {

	@Value("${jasypt.encryptor.password}")
	private String SECRET_KEY;

	private static final String ALGORITHM = "PBEWithHMACSHA512AndAES_256";

	@Bean("jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
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

		return encryptor;
	}
}
