package ro.teamnet.zth.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author: Ovidiu
 * Date:   5/5/2015
 */
public class HttpSessionServlet extends HttpServlet {

    // Default username and password
    private static final String defaultUser = "admin";
    private static final String defaultPassword = "pass";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Cookie[] cookies = req.getCookies();

        HttpSession session = req.getSession();

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        if (username.equals(defaultUser) && password.equals(defaultPassword)) {
            writer.write("Welcome back!\n Username: " + username + "\n");

            for (Cookie cookie : cookies)
                writer.write(cookie.getName() + ":" + cookie.getValue() + "\n");

            writer.write(session.getId());
        } else {
            session.setAttribute("user", username);
            session.setAttribute("session", session);
            resp.sendRedirect("/httpsession/loginFail.jsp");
        }
    }

}