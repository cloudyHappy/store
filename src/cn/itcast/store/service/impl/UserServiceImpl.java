package cn.itcast.store.service.impl;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.dao.impl.UserDaoImpl;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void register(User user) throws Exception {
        userDao.save(user);
    }

    @Override
    public boolean activate(String code) throws SQLException {
        String id = userDao.getIdByCode(code);
        if (id != null) {
            userDao.activateUser(id);
            return true;
        } else {
            //未查询到激活码对应的用户
            return false;
        }
    }

    @Override
    public User userLogin(String username, String password) throws SQLException {

        User user = userDao.userLogin(username, password);
        if (user == null) {
            //账户不存在
            throw new RuntimeException("密码错误,请重试!");
        } else if (user.getState() == 0) {
            //账户未激活
            throw new RuntimeException("该账户未激活,请登录邮箱激活后使用!");
        } else {
            return user;
        }
    }

    @Override
    public boolean existsUserName(String username) throws SQLException {
        return userDao.existsUserName(username);
    }
}
