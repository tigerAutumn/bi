package com.pinting.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 默认信任服务端根证书
 */
public class DefaultTrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		return;
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		return;
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
