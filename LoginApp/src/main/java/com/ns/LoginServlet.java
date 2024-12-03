package com.ns;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpServlet;

//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet 
{
	String url = "jdbc:mysql://localhost:3306/customer";
	String un = "root";
	String pass = "murthy";
	private Connection con;
	private String checkEmail = "select * from customer where email = ?";
	private PreparedStatement pstmt;
	private ResultSet resultSet;
	
	@Override
	public void service(ServletRequest req, ServletResponse resp) 
			throws ServletException, IOException 
	{	
		HttpServletResponse httpResp = (HttpServletResponse) resp;
		String email = req.getParameter("email");
		String password = req.getParameter("pwd");
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, un, pass);
			pstmt = con.prepareStatement(checkEmail);
			pstmt.setString(1, email);
			resultSet = pstmt.executeQuery();
			
			if(resultSet.next())
			{
				if(password.equals(resultSet.getString("password")))
				{
					httpResp.sendRedirect("LoginSuccess.html");
				}
				else
				{
					httpResp.sendRedirect("PassMissMatch.html");
					
				}
			}
			else
			{
				httpResp.sendRedirect("InvalidUser.html");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
