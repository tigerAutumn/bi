package com.pinting.core.util.encrypt;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSAKey {
	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;

	public RSAPublicKey getPublicKey() {
		return this.publicKey;
	}

	public void setPublicKey(RSAPublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public RSAPrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public void setPrivateKey(RSAPrivateKey privateKey) {
		this.privateKey = privateKey;
	}
}