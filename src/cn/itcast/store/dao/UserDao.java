package cn.itcast.store.dao;

import cn.itcast.store.domain.User;

import java.sql.SQLException;

public interface UserDao {
    /**
     * 保存用户的方法
     * @param user
     * @throws SQLException
     */
    void save(User user)throws SQLException;


    /**
     * 根据验证码获取id
     * @param code 验证码
     * @return
     * @throws SQLException
     */
    String getIdByCode(String code)throws SQLException;


    /**
     * 激活指定id用户
     * @param id 被激活的用户id
     * @throws SQLException
     */
    void activateUser(String id)throws SQLException;

    /**
     * 根据用户名和密码获取用户
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User userLogin(String username, String password) throws SQLException;

    /**
     * 检查用户名是否已经存在
     * @param userName
     * @return
     */
    boolean existsUserName(String userName) throws SQLException;
}
