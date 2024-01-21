/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.service;

import com.pinting.cfcatransfer.bigangwan.model.cfca.MakeNamedSealReq;
import com.pinting.cfcatransfer.bigangwan.model.cfca.MakeSealReq;
import com.pinting.cfcatransfer.bigangwan.model.cfca.SealAutoPdfReq;


/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: SealServiceClient.java, v 0.1 2015-9-15 下午1:30:02 BabyShark Exp $
 */
public interface SealServiceClient {

    /**
     * 普通制章
     * 
     * @param req
     * @return
     */
    public boolean makeSeal(MakeSealReq req);

    /**
     * 制人名章
     * 
     * @param req
     * @return
     */
    public boolean makeNamedSeal(MakeNamedSealReq req);

    /**
     * PDF自动化签章
     * 
     * @param req
     * @return
     * @throws Exception
     */
    public boolean sealAutoPdf(SealAutoPdfReq req);

}
