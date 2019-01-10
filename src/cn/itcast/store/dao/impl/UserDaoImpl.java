package cn.itcast.store.dao.impl;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
    @Override
    public void save(User user) throws SQLException {
        String sql = "insert into user(uid,username,password,name,email,telephone,birthday,sex,state,code) values(?,?,?,?,?,?,?,?,?,?)";
        Object[] params = new Object[]{user.getUid(), user.getUsername(), user.getPassword(),
                user.getName(), user.getEmail(), user.getTelephone(), user.getBirthday(), user.getSex(),
                user.getState(), user.getCode()};

        runner.update(sql,params);
    }

    @Override
    public String getIdByCode(String code) throws SQLException {
        String sql = "select uid from user where code = ?";
        String result = (String) runner.query(sql, new ScalarHandler(),code);
        return result;
    }

    @Override
    public boolean existsUserName(String userName) throws SQLException {
        String sql = "select count(1) from user where username = ?";
        Long result = (Long) runner.query(sql, new ScalarHandler(), userName);
        return result>0;
    }

    @Override
    public void activateUser(String id) throws SQLException {
        String sql = "update user set state = 1,code = null where uid = ?";
        runner.update(sql,id);
    }

    @Override
    public User userLogin(String username, String password) throws SQLException {
        String sql = "select * from user where username = ? and password = ?";
        User user = runner.query(sql, new BeanHandler<User>(User.class), username, password);
        return user;
    }
}
