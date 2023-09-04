package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionException;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final static String CREATE = "CREATE TABLE IF NOT EXISTS User (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastname VARCHAR(45), age TINYINT(3))";
    private final static String DROP = "DROP TABLE IF EXISTS User";
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(CREATE).executeUpdate();
            session.getTransaction().commit();
        } catch (SessionException e){
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(DROP).executeUpdate();
            session.getTransaction().commit();
        } catch (SessionException e){
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SessionException e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User userDB = session.get(User.class, id);
            session.delete(userDB);
            session.getTransaction().commit();
        } catch (SessionException e){
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            userList = session.createQuery("from User").list();
            session.getTransaction().commit();
        } catch (SessionException e){
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (SessionException e){
            e.printStackTrace();
        }
    }
}
