
package com.studybuddy.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.bson.Document;

import com.studybuddy.services.StudentDAO;

@WebServlet("/LoginPageServlet")
public class LoginPageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendError(
            HttpServletResponse.SC_METHOD_NOT_ALLOWED,
            "Please login using POST."
        );
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Step-1: Receive Login details

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Step-2: Validate input

        if (email == null || email.trim().isEmpty() ||
            password == null || password.isEmpty()) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Please enter email and password."
            );
            return;
        }

        email = email.trim();

        // Step-3: Find user in MongoDB

        StudentDAO dao = new StudentDAO();

        Document user = dao.findUserForLogin(email, password);

        // Step-4: Check login details

        if (user == null) {

            response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Invalid Email or Password."
            );
            return;
        }

        // Step-5: Retrieve User ID

        String userID = user.getString("userID");

        if (userID == null || userID.trim().isEmpty()) {

            response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "User account information is incomplete."
            );
            return;
        }

        // Step-6: Create Login Session

        HttpSession session = request.getSession(true);

        request.changeSessionId();

        session.setAttribute("userID", userID);
        session.setAttribute("userEmail", email);
        session.setAttribute("isLoggedIn", true);

        System.out.println("Login Successful");
        System.out.println("User ID: " + userID);

        // Step-7: Redirect after successful login

        response.sendRedirect(
            request.getContextPath() + "/index.html"
        );
    }
}
