package com.websitte.jdbc.utils;

import com.websitte.jdbc.dao.UsersDAO;
import com.websitte.jdbc.model.User;

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
                case "1" ->
                    createUser();
                case "2" ->
                    System.out.println("\t\t\t2");

                case "3" ->

                    System.out.println("\t\t\t3");

                case "4" ->

                    System.out.println("\t\t\t4");

                case "5" ->

                    System.out.println("\t\t\t5");

                default -> {
                    System.out.println("Wrong option (!)");
                    menu();
                }

            }
        } while (!option.equals("5"));
    }

    public static void createUser() throws IOException {
        System.out.print("Enter login --> ");
        String login = br.readLine();
        System.out.println("Enter password --> ");
        String password = br.readLine();
        System.out.println("Enter email --> ");
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
