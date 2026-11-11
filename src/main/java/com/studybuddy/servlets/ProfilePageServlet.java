
package com.studybuddy.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.studybuddy.schemas.UserSchema;
import com.studybuddy.services.StudentDAO;

@WebServlet("/ProfilePageServlet")
public class ProfilePageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Get existing session
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("RegistrationPage.html");
            return;
        }

        // Get registered user data
        UserSchema userData =
                (UserSchema) session.getAttribute("userData");

        if (userData == null) {
            response.sendRedirect("RegistrationPage.html");
            return;
        }

        // Receive User ID from Profile Page
        String userID = request.getParameter("userID");

        if (userID == null || userID.trim().isEmpty()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Please enter a User ID."
            );
            return;
        }

        userID = userID.trim().toUpperCase();

        // Check whether User ID already exists
        StudentDAO dao = new StudentDAO();

        if (dao.isUserIDExists(userID)) {
            response.sendError(
                    HttpServletResponse.SC_CONFLICT,
                    "User ID already used. Please try another ID."
            );
            return;
        }

        // Assign User ID to UserSchema
        userData.setUserID(userID);

        // Get profile information
        String[] subjectsArray =
                request.getParameterValues("subjects");

        String[] interestArray =
                request.getParameterValues("interest");

        String studyMode =
                request.getParameter("studyMode");

        String[] studyTimeArray =
                request.getParameterValues("studyTime");

        // Convert arrays into lists
        List<String> subjects = subjectsArray == null
                ? null : Arrays.asList(subjectsArray);

        List<String> interests = interestArray == null
                ? null : Arrays.asList(interestArray);

        List<String> studyTime = studyTimeArray == null
                ? null : Arrays.asList(studyTimeArray);

        // Add profile information to UserSchema
        userData.setSubjects(subjects);
        userData.setInterest(interests);
        userData.setStudyMode(studyMode);
        userData.setStudyTime(studyTime);

        // Save complete student information
        boolean saved = dao.saveStudent(userData);

        if (saved) {
            session.removeAttribute("userData");
            response.sendRedirect("LoginPage.html");

        } else {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to save student information."
            );
        }
    }
}
