package com.pinting.manage.service;

public interface MUserService {

    /**
     * 重置管理员登录密码
     *
     * @param id
     */
    void resetPassword(Integer id);

    /**
     * 冻结管理员账号
     * @param id
     */
    void freezeMUser(Integer id);

    /**
     * 解冻管理台账号
     * @param id
     */
    void UnfreezeMUser(Integer id);
}
