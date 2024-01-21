package com.pinting.core.converter;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.pinting.core.util.encrypt.BASE64Encoder;
import com.pinting.core.util.encrypt.BASE64Util;
import com.pinting.core.util.encrypt.RSAUtil;

public class LocalPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	
	private final static String PRIVATEKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIPlzY3+4mOUUbAHfdczdaUMncmikkV6pDWZ3uEWSrKFmMjJKKmegf6uaUbGojn+9HH55eWV23nCaQcFCQTrxLeEsvyzodWcJcnA/mVYEcLWBN1h9e7CGLeWtq12mYT2cLbexNW5VKBXq4aKkUEFpwAoYnxx7uVdJ/AlFfmstsilAgMBAAECgYB6KjYqh+Lt8ql6jmXLFjmn5ria0r4CJmbD0sHx3zDOixkJF/BWsp6a4rZVvJLzfqawjpKWBCglbaDTxKEm8qzfKNDYlvH9uiXXeVPDPtQ6aTwhlz0YprlNk5W+brtQ06TI4j/XXyjLD6sKONEEABW8vsyIbmsdGuFQvqL5+V5S4QJBANhhGukSAJLvXQGnSYQNu91xjpMnTJ4I4htqjvhY65U+ezKe323UYNsX4wdUTO2iB7vag3sSLd1wf17IKcE2dSkCQQCcDJNrmb6S1QnQqhEPBgRO0Ip5VaZDE6Y+Ombaa5n+3oyiNHASeQF8GpvB6WqHzF6WihfH7+mDUytbRZtWBMsdAkB/3aJRaNn9+oUznO/MoXELAzgFrYuROB6lpOqnx6QvpaSX3VqbRI2X6x22DxMGumFnmVFpQOzplrkuAYrrKotBAkBb80kWuTRDXwb2jo+Ys6nsKxO9ZUuf8L8fodgjUg4e0VI67f0v1V+bmi43d+vO8wy1Vc+nf2WwfVxp3r0mVZK9AkEAgT8vUTJww82gruzJz6+BDTlk8vWN15fllC2AFKgvRiTS0K+414ChOZu4OThmLAdF6H+gINCdtMCcl1QORF/qCg==";
	
	@Override
	protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode)
    {
        String propVal = null;
        if(systemPropertiesMode == 2)
            propVal = resolveSystemProperty(placeholder);
        if(propVal == null)
            propVal = resolvePlaceholder(placeholder, props);
        if(propVal == null && systemPropertiesMode == 1)
            propVal = resolveSystemProperty(placeholder);
        return propVal;
    }
	
	@Override
    protected String resolvePlaceholder(String placeholder, Properties props)
    {
        if("dataSource.driver".equals(placeholder) || "dataSource.url".equals(placeholder) || 
        		"dataSource.username".equals(placeholder) || "dataSource.password".equals(placeholder)){
        	String value = props.getProperty(placeholder);
        	try {
        		value = new String(RSAUtil.decryptByPrivateKey(BASE64Util.decryptBASE64(value), PRIVATEKEY));
        		return value;
			} catch (Exception e) {
				//e.printStackTrace();
			}
        }
		return props.getProperty(placeholder);
    }
	
}
