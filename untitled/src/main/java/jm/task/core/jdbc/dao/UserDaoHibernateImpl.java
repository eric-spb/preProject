package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.*;
import org.hibernate.*;

import javax.persistence.Entity;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final Session session;

    public UserDaoHibernateImpl() {
        this.session = sessionFactory.openSession();
    }

    //константы, которые будет понимать MySQL
    private static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50) NOT NULL," +
            "lastName VARCHAR(50) NOT NULL, age TINYINT NOT NULL)";
    private static final String DROP_SQL = "DROP TABLE IF EXISTS users";



    @Override
    public void createUsersTable() {
        Transaction ts = null;
        try {
            ts = session.beginTransaction();
            session.createSQLQuery(CREATE_SQL).executeUpdate();
            ts.commit();
        } catch (Exception e) {
            if (ts != null) {
                ts.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction ts = null;
        try {
            ts = session.beginTransaction();
            session.createSQLQuery(DROP_SQL).executeUpdate();
            ts.commit();
        } catch (Exception e) {
            if (ts != null) {
                ts.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction ts = null;
        try {
            ts = session.beginTransaction();
            session.save(new User(name, lastName, age));
            ts.commit();
        } catch (Exception e) {
            if (ts != null) {
                ts.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction ts = null;
        try {
            ts = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            ts.commit();
        } catch (Exception e) {
            if (ts != null) {
                ts.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return session.createQuery("FROM User", User.class).list();
    }

    @Override
    public void cleanUsersTable() {
        Transaction ts = null;
        try {
            ts = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            ts.commit();
        } catch (Exception e) {
            if (ts != null) {
                ts.rollback();
            }
            e.printStackTrace();
        }
    }
}
