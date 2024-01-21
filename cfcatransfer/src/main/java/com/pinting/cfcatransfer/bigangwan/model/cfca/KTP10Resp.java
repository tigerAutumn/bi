/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.model.cfca;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: KTP10Resp.java, v 0.1 2015-9-14 下午3:41:15 BabyShark Exp $
 */
public class KTP10Resp extends KTBaseResp {
    private String csr;
    private String keyIdentifier;

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }

    public String getKeyIdentifier() {
        return keyIdentifier;
    }

    public void setKeyIdentifier(String keyIdentifier) {
        this.keyIdentifier = keyIdentifier;
    }
}
