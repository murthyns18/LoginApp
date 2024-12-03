package com.ns;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CollectData")
public class CollectData extends HttpServlet
{
	private PrintWriter pw;
	private RequestDispatcher rd;
	private Connection con;
	private PreparedStatement pstmt;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException 
	{
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String mobile = req.getParameter("mobile");
		String address = req.getParameter("address");
		String city = req.getParameter("city");
		String password = req.getParameter("password");
		String cpassword = req.getParameter("cpassword");

		String insert = "insert into customer(username, email, mobile, address, city, password) values (?,?,?,?,?,?)";
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer", "root", "murthy");
			
			if(password.equals(cpassword))
			{
				pstmt = con.prepareStatement(insert);
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				pstmt.setString(3, mobile);
				pstmt.setString(4, address);
				pstmt.setString(5, city);
				pstmt.setString(6, password);
				
				int x = pstmt.executeUpdate();
				if(x==0)
				{
					resp.sendRedirect("Failure.html");
				}
				else
				{
					resp.sendRedirect("Success.html");
					
				}
			}
			else
			{
				resp.sendRedirect("PassMissMatch.html");
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
