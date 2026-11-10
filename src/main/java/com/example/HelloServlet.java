package main.java.com.example;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException; // Use javax.servlet if Tomcat 9 or below
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h1>Hello from your manually compiled Java Servlet!</h1>");
        out.println("</body></html>");
    }
}
