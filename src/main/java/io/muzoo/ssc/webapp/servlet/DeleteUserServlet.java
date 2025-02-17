/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author gigadot
 */
public class DeleteUserServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/user/delete";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (authorized) {
            // do MVC in here
            String username = (String) request.getSession().getAttribute("username");
            UserService userService = UserService.getInstance();

            try {
                User currentUser = userService.findByUsername(username);
                User userToDelete = userService.findByUsername(request.getParameter("username"));

                if(StringUtils.equals(currentUser.getUsername(), userToDelete.getUsername())) {
                    request.getSession().setAttribute("hasError", true);
                    request.getSession().setAttribute("message", "You cannot delete your own account");
                }
                else {
                    if (userService.deleteUser(userToDelete.getUsername())){
                        request.getSession().setAttribute("hasError", false);
                        request.getSession().setAttribute("message", "User deleted successfully");
                    } else {
                        request.getSession().setAttribute("hasError", true);
                        request.getSession().setAttribute("message", "Unable to delete user");
                    }
                }
            }catch (Exception e) {
                request.getSession().setAttribute("hasError", true);
                request.getSession().setAttribute("message", "Unable to delete user");

            }
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/login");
        }
    }
}
