package io.muzoo.ssc.webapp.service;

import io.muzoo.ssc.webapp.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final String INSERT_USER_SQL = "INSERT INTO tbl_usr (username, password, display_name) VALUES (?, ?, ?)";
    private static final String SELECT_USER_SQL = "SELECT * FROM tbl_usr WHERE username = ?";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM tbl_usr;";
    private static final String DELETE_USER_SQL = "DELETE FROM tbl_usr WHERE username = ?";
    private static final String UPDATE_USER_SQL = "UPDATE tbl_usr SET display_name = ? WHERE username = ?;";
    private static final String UPDATE_USER_PASSWORD_SQL = "UPDATE tbl_usr SET password = ? WHERE username = ?;";



    private DatabaseConnectionService dc;
    private static UserService service;

    public UserService() {
    }

    public static UserService getInstance() {
        if (service == null) {
            service = new UserService();
            service.setDc(DatabaseConnectionService.getInstance());
        }
        return service;
    }

    public void setDc(DatabaseConnectionService dc) {
        this.dc = dc;
    }

    //create user
    public void createUser(String username, String password, String displayName) throws UserServiceException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        try(
                Connection connection = dc.getConnection();
              PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL)
        ) {
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setString(3, displayName);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new UsernameNotUniqueException(String.format("Username '%s' already exists.", username));
        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }
    }

    public User findByUsername(String username) {
        try(
                Connection connection = dc.getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL);
                ) {
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();

            return new User(resultSet.getLong("id")
                    , resultSet.getString("username")
                    , resultSet.getString("password")
                    , resultSet.getString("display_name"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = dc.getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS_SQL);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                users.add(new User(resultSet.getLong("id")
                        , resultSet.getString("username")
                        , resultSet.getString("password")
                        , resultSet.getString("display_name")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public boolean deleteUser(String username) {

        try(
                Connection connection = dc.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL)
        ) {
            ps.setString(1, username);
            int deleteCount = ps.executeUpdate();
            connection.commit();

            return deleteCount >0;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public void updateUser(String username,String displayName) throws UserServiceException{
        try(
                Connection connection = dc.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_USER_SQL)
        ) {
            ps.setString(1, displayName);
            ps.setString(2, username);

            ps.executeUpdate();
            connection.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new UsernameNotUniqueException(String.format("Username '%s' already exists.", username));
        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }
    }

    public void changePassword(String username,String newPassword) throws UserServiceException{

        try(
                Connection connection = dc.getConnection();
                PreparedStatement ps = connection.prepareStatement(UPDATE_USER_PASSWORD_SQL)
        ) {
            ps.setString(1, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            ps.setString(2, username);

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }
    }

    public static void main(String[] args) throws UserServiceException {
        UserService userService = UserService.getInstance();
        userService.createUser("Lol","lol","Lol");
    }
}















