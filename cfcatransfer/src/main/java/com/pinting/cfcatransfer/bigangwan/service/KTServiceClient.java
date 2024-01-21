/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.service;

import cfca.kt.vo.KeyPairRequestVO;
import cfca.kt.vo.KeyPairResponseVO;
import cfca.kt.vo.PFXRequestVO;
import cfca.kt.vo.PFXResponseVO;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: KTServiceClient.java, v 0.1 2015-9-14 下午4:37:29 BabyShark Exp $
 */
public interface KTServiceClient {

    /**
     * 产生私钥及P10
     * 
     * @param req
     * @return 失败返回null
     */
    public KeyPairResponseVO createKeyPair(KeyPairRequestVO req);

    /**
     * 生成pfx格式证书
     * 
     * @param req
     * @return 失败返回null
     */
    public PFXResponseVO createPFX(PFXRequestVO req);

}
