package ro.teamnet.zth.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author: Ovidiu
 * Date:   5/4/2015
 */
public class ZeroToHeroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        PrintWriter writer = resp.getWriter();
        writer.write(handleRequest(req));
    }

    private String handleRequest(HttpServletRequest req) {
        return "Hello <b>" +
                req.getParameter("first-name") + " " +
                req.getParameter("last-name") + "</b>!\n" +
                "Enjoy Zero To Hero!!!";
    }

}
