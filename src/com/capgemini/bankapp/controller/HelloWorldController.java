package com.capgemini.bankapp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "helloWorld", loadOnStartup = 1, urlPatterns = {"/hello","/helloWorld","/login"})
public class HelloWorldController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public HelloWorldController() {
       
    }
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		System.out.println("Hello world");   //This will print on server console
		
		PrintWriter out = response.getWriter();
		out.println("Hello World!!!");
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	RequestDispatcher dispatcher = null;
    	response.setContentType("text/html");
    	//PrintWriter out = response.getWriter();
    	if(username.equals("root")&&password.equals("root123")) {
    		//out.println("<h2> Login Successful. Welcome "+username + "</h2>");
    		dispatcher = request.getRequestDispatcher("success.html");
    	}
    	else
    	{
    		//out.println("<h2>invalid username and password</h2>");
    		dispatcher = request.getRequestDispatcher("error.html");
    	}
    	dispatcher.forward(request, response);
    }
    
}
