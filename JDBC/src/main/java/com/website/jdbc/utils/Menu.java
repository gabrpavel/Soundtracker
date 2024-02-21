package com.website.jdbc.utils;

import com.website.jdbc.dao.UsersDAO;
import com.website.jdbc.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class Menu {

    private Menu() {
        throw new IllegalStateException("Utility class");
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static Logger logger = Logger.getLogger(Menu.class.getName());
    static UsersDAO usersDAO = new UsersDAO();

    public static void menu() throws IOException, SQLException {

        String option;

        do {
            logger.info("""
                    \t\t\t --------- CRUD OPERATIONs USING JDBC --------
                    \t\t\t1 - Create User
                    \t\t\t2 - Read User
                    \t\t\t3 - Update User
                    \t\t\t4 - Delete User
                    \t\t\t5 - Exit
                    \t\t\tSelect Option: \s
                    """);
            option = br.readLine();

            switch (option) {
                case "1" ->
                    createUser();
                case "2" ->
                    getAllUsers();
                case "3" ->
                    updateUser();
                case "4" ->
                    deleteUser();
                case "5" -> {
                    return;
                }
                default -> {
                    logger.info("Wrong option (!)");
                    menu();
                }

            }
        } while (true);
    }

    public static void createUser() throws IOException, SQLException {
        logger.info("Enter login --> ");
        String login = br.readLine();
        logger.info("Enter password --> ");
        String password = br.readLine();
        logger.info("Enter email --> ");
        String email = br.readLine();

        User user = new User(0L, login, password, email);
        int status = usersDAO.saveUser(user);

        if(status == 1 )
            logger.info("User saved successfully");
        else
            logger.info("ERROR while saving user");
        logger.info("\n");
    }

    public static void getAllUsers() {
        List<User> users = usersDAO.getAllUsers();
        for (User user: users) {
            showUser(user);
        }

    }
    public static void showUser(User user) {
        String string = user.toString();
        logger.info(string);
    }

    public static void updateUser() throws IOException, SQLException {

        logger.info("User ID: ");
        Long id = Long.valueOf(br.readLine());
        logger.info("New email: ");
        String newEmail = br.readLine();

        User user = usersDAO.getUserByID(id);
        user.setEmail(newEmail);
        int status = usersDAO.updateUser(user);
        if (status == 1) logger.info("User updated successfully");
        else
            logger.info("ERROR while updating user");
    }

    public static void deleteUser() throws IOException {
        logger.info("User ID: ");
        Long id = Long.valueOf(br.readLine());
        int status = usersDAO.deleteUser(id);
        if (status == 1) logger.info("User deleted successfully");
        else
            logger.info("ERROR while deleting user");
    }
}
