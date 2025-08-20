package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;

import static jm.task.core.jdbc.util.Util.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException {
        /* реализуйте алгоритм здесь
        try(Connection con = getConnection()) {
          if (con != null && con.isValid(2)) {
            System.out.println("Есть контакт");
        } else {
           System.out.println("Не работает");
        }
        } catch (SQLException e) {
        System.out.println("Пошёл отсуда, тут ошибка");
        e.printStackTrace();
        }
         */

        Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Name1", "LastName1", (byte) 20);
        userDao.saveUser("Name2", "LastName2", (byte) 25);
        userDao.saveUser("Name3", "LastName3", (byte) 31);
        userDao.saveUser("Name4", "LastName4", (byte) 38);

        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
