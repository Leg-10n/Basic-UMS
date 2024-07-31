package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.Routable;
import io.muzoo.ssc.webapp.model.User;
import io.muzoo.ssc.webapp.service.SecurityService;
import io.muzoo.ssc.webapp.service.UserService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangePasswordServlet extends HttpServlet implements Routable {
    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/user/password";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (authorized) {
            String username = StringUtils.trim((String) request.getParameter("username"));
            UserService userService = UserService.getInstance();

//
            User user = userService.findByUsername(username);
            request.setAttribute("user", user);
            request.setAttribute("username", user.getUsername());

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/password.jsp");
            rd.include(request, response);

            request.getSession().removeAttribute("hasError");
            request.getSession().removeAttribute("message");
        } else {
            request.removeAttribute("hasError");
            request.removeAttribute("message");
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (authorized) {

            String errorMessage = null;

            String username = StringUtils.trim(request.getParameter("username"));
            String password = request.getParameter("password");
            String cpassword = request.getParameter("cpassword");

            UserService userService = UserService.getInstance();
            User user = userService.findByUsername(username);


            if(user == null) {
                errorMessage = String.format("Username '%s' doesn't exists", username);
            }

            else if (StringUtils.isBlank(password)) {
                errorMessage = "Password cannot be empty";
            }

            else if(!StringUtils.equals(password, cpassword)) {
                errorMessage = "Passwords do not match. Please re-enter the password";
            }


            if(errorMessage != null) {
                request.getSession().setAttribute("hasError", true);
                request.getSession().setAttribute("message", errorMessage);
            } else {
                try {
                    userService.changePassword(username,password);

                    request.getSession().setAttribute("hasError", false);
                    request.getSession().setAttribute("message", "Password has been updated successfully");
                    response.sendRedirect("/");
                    return;
                } catch (Exception e) {
                    request.getSession().setAttribute("hasError", true);
                    request.getSession().setAttribute("message",e.getMessage());
                }
            }
            request.setAttribute("username", username);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/password.jsp");
            rd.include(request, response);

            request.getSession().removeAttribute("hasError");
            request.getSession().removeAttribute("message");
        } else {
            request.removeAttribute("hasError");
            request.removeAttribute("message");
            response.sendRedirect("/login");
        }
    }
}
