
package com.studybuddy.servlets;

import java.io.IOException;
import java.security.SecureRandom;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.studybuddy.helper.DataValidation;
import com.studybuddy.schemas.UserSchema;
import com.studybuddy.services.EmailServices;
import com.studybuddy.services.JWTServices;
import com.studybuddy.services.StudentDAO;

@WebServlet("/RegistrationPageServlet")
public class RegistrationPageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final SecureRandom random = new SecureRandom();

    public RegistrationPageServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendError(
                HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                "Please submit the registration form using POST."
        );
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Registration form data received.");

        // Receiving registration data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String course = request.getParameter("course");
        String branch = request.getParameter("branch");
        String semester = request.getParameter("semester");
        String password = request.getParameter("password");
        String confirmPassword =
                request.getParameter("confirmPassword");

        // Normalize email
        if (email != null) {
            email = email.trim().toLowerCase();
        }

        // Data validation
        boolean isPasswordValid =
                DataValidation.validatePasswords(
                        password, confirmPassword
                );

        boolean isEmailValid =
                DataValidation.validateEmail(email);

        boolean isNamesValid =
                DataValidation.validateName(name);

        System.out.println("Password Valid: " + isPasswordValid);
        System.out.println("Email Valid: " + isEmailValid);
        System.out.println("Name Valid: " + isNamesValid);

        // Check invalid registration details
        if (!isPasswordValid || !isEmailValid || !isNamesValid) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid registration details."
            );

            return;
        }

        // Create StudentDAO object
        StudentDAO dao = new StudentDAO();

        // Check duplicate email
        if (dao.isEmailExists(email)) {

            System.out.println("Email already registered: " + email);

            response.sendRedirect(
                    request.getContextPath()
                    + "/RegistrationPage.html?error=emailExists"
            );

            return;
        }

        // Creating UserSchema object
        UserSchema newUser = new UserSchema(
                null,
                name,
                email,
                course,
                branch,
                semester,
                password
        );

        System.out.println("UserSchema created.");

        // Generate six-digit OTP
        int OTP = 100000 + random.nextInt(900000);

        System.out.println("OTP generated.");

        // Generate JWT token
        String OTPToken = JWTServices.generateJWT(OTP);

        if (OTPToken == null) {

            System.out.println("JWT generation failed.");

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to generate OTP. Please try again."
            );

            return;
        }

        System.out.println("JWT token generated successfully.");

        // Send OTP to user's email
        System.out.println("Sending OTP email...");

        Boolean isEmailSent =
                EmailServices.sendOTP(email, name, OTP);

        if (Boolean.TRUE.equals(isEmailSent)) {

            // Create session
            HttpSession session = request.getSession();

            // Store registration information temporarily
            session.setAttribute("userData", newUser);

            session.setAttribute("OTPToken", OTPToken);

            System.out.println(
                    "OTP successfully sent to: " + email
            );

            // Redirect to OTP page
            response.sendRedirect(
                    request.getContextPath() + "/OTPPage.html"
            );

        } else {

            System.out.println("OTP email sending failed.");

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to send OTP. Please try again."
            );
        }
    }
}
