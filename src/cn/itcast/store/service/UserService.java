package cn.itcast.store.service;

import cn.itcast.store.domain.User;

import java.sql.SQLException;

public interface UserService {
    void register(User user)throws Exception;

    /**
     * 根据激活码激活账户
     * @param code 激活码
     * @return
     * @throws SQLException
     */
    boolean activate(String code)throws SQLException;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    User userLogin(String username, String password)throws  SQLException;

    /**
     * 检查用户名是否已经存在
     * @param username
     * @return
     * @throws SQLException
     */
    boolean existsUserName(String username)throws  SQLException;
}
