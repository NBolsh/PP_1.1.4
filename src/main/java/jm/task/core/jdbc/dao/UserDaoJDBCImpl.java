package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final static String CREATING = "CREATE TABLE Users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastname VARCHAR(45), age TINYINT(3))";
    private final static String INSERT = "INSERT INTO Users (name, lastname, age) VALUES (?, ?, ?)";
    private final static String DELETING = "DELETE FROM Users  WHERE id = ?";
    private final static String GET_ALL = "SELECT * FROM Users";

    public UserDaoJDBCImpl() {
    }
@Override
    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(CREATING);
        } catch (SQLException e) {
        }
    }
@Override
    public void dropUsersTable() {
        try(Statement statement = Util.getConnection().createStatement()) {
            String sqlCommand = "DROP TABLE Users";
            statement.execute(sqlCommand);
        } catch (SQLException e) {
        }

    }
@Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(INSERT)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
@Override
    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement(DELETING)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
@Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return userList;
    }
@Override
    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String sqlCommand = "TRUNCATE Users";
            statement.executeUpdate(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
