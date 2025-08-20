package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    //Надо еще будет сделать открытое соединение (как?)
    private Connection connection;

    //константы, которые будет понимать MySQL
    private static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50) NOT NULL," +
            "lastName VARCHAR(50) NOT NULL, age TINYINT NOT NULL)";
    private static final String DROP_SQL = "DROP TABLE IF EXISTS users";
    private static final String INSERT_SQL = "INSERT INTO users(name, lastName, age) VALUES (?, ?, ?)";
    private static final String DELETE_SQL = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_SQL = "SELECT id, name, lastName, age FROM users";
    private static final String CLEAN_SQL = "DELETE FROM users";


    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate(CREATE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement st = connection.createStatement()) {
             st.executeUpdate(DROP_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_SQL)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User с именем - " + name +  " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_SQL)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        System.out.println(users);
        try (Connection conn = Util.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_SQL)) {

            while (rs.next()) {
                User user = new User();
                        user.setName(rs.getString("name"));
                        user.setLastName(rs.getString("lastName"));
                                user.setAge(rs.getByte("age"));
                user.setId(rs.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (users.isEmpty()) {
            System.out.println("В таблице нет пользователей.");
        } else {
            users.forEach(System.out::println);
        }
        return users;

    }

    @Override
    public void cleanUsersTable() {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate(CLEAN_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
