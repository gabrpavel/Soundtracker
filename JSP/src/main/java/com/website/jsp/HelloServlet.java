package com.website.jsp;

import com.website.jdbc.dao.UsersDAO;
import com.website.jdbc.model.User;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");

        PrintWriter pw = response.getWriter();

        UsersDAO usersDAO = new UsersDAO();

        List<User> users = usersDAO.getAllUsers();
        for (User user: users) {
            pw.println("<p>Login: "+user.getLogin()+"\tEmail: "+user.getEmail()+"</p>");
        }
    }
}
