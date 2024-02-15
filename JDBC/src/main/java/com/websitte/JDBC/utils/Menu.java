package com.websitte.JDBC.utils;

import com.websitte.JDBC.dao.UsersDAO;
import com.websitte.JDBC.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static UsersDAO usersDAO = new UsersDAO();

    public static void menu() throws IOException {

        String option = "";

        do {
            System.out.println("\n\n\t\t\t --------- CRUD OPERATIONs USING JDBC --------\n");
            System.out.println("\t\t\t1 - Create User ");
            System.out.println("\t\t\t2 - Read User ");
            System.out.println("\t\t\t3 - Update User ");
            System.out.println("\t\t\t4 - Delete User ");
            System.out.println("\t\t\t5 - Exit\n");
            System.out.print("\t\t\tSelect Option  ---->>>  ");
            option = br.readLine();

            switch (option) {
                case "1" -> {
                    createUser();
                }
                case "2" -> {
                    System.out.println("\t\t\t5 - Exit");
                }
                case "3" -> {

                    System.out.println("\t\t\t5 - Exit");
                }
                case "4" -> {

                    System.out.println("\t\t\t5 - Exit");
                }
                case "5" -> {

                    System.out.println("\t\t\t5 - Exit");
                }
                default -> {
                    System.out.println("Wrong option (!)");
                    menu();
                }

            }
        } while (!option.equals("5"));
    }

    public static void createUser() throws IOException {
        System.out.println("------------------------------------------------");
        System.out.println("Enter login:");
        System.out.println("------------------------------------------------");
        String login = br.readLine();
        System.out.println("------------------------------------------------");
        System.out.println("Enter password:");
        System.out.println("------------------------------------------------");
        String password = br.readLine();
        System.out.println("------------------------------------------------");
        System.out.println("Enter email:");
        System.out.println("------------------------------------------------");
        String email = br.readLine();

        User user = new User(0L, login, password, email);
        int status = usersDAO.saveUser(user);

        if(status == 1 )
            System.out.println("User saved successfully");
        else
            System.out.println("ERROR while saving user");
        System.out.println("\n");
    }
}
