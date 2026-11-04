package com.studybuddy.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.studybuddy.schemas.UserSchema;
import com.studybuddy.services.JWTServices;

@WebServlet("/OTPValidationServlet")
public class OTPValidationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public OTPValidationServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendError(
                HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                "Please submit OTP using POST."
        );
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Step 1: Getting six OTP digits

        String[] otpDigits = {
            request.getParameter("otp1"),
            request.getParameter("otp2"),
            request.getParameter("otp3"),
            request.getParameter("otp4"),
            request.getParameter("otp5"),
            request.getParameter("otp6")
        };

        // Step 2: Checking whether all OTP fields are filled

        StringBuilder enteredOTP = new StringBuilder();

        for (String digit : otpDigits) {

            if (digit == null || !digit.matches("[0-9]")) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Please enter all six OTP digits."
                );

                return;
            }

            enteredOTP.append(digit);
        }

        String OTP = enteredOTP.toString();

        // Step 3: Getting existing session

        HttpSession session = request.getSession(false);

        if (session == null) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Session expired. Please register again."
            );

            return;
        }

        // Step 4: Getting stored registration data and JWT token

        String OTPToken =
                (String) session.getAttribute("OTPToken");

        UserSchema userData =
                (UserSchema) session.getAttribute("userData");

        if (OTPToken == null || userData == null) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "OTP session expired. Please register again."
            );

            return;
        }

        // Step 5: Verifying OTP using JWT

        String storedOTP = JWTServices.verifyJWT(OTPToken);

        if (storedOTP == null || !storedOTP.equals(OTP)) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Incorrect or expired OTP."
            );

            return;
        }

        // Step 6: OTP successfully verified

        session.setAttribute("isEmailVerified", true);

        System.out.println("Email verification successful!");
        System.out.println("Verified email: " + userData.getEmail());

        // Step 7: Redirecting to profile page

        response.sendRedirect(
                request.getContextPath() + "/ProfilePage.html"
        );
    }
}
