package ncu.im3069.demo.controller;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import ncu.im3069.demo.util.DBMgr;
 
 
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
 
 
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected Connection conn = null;
	
//	private static String DB_Driver = "com.mysql.jdbc.Driver";
//	private static String DB_URL = "jdbc:mysql://localhost/user?useSSL=false"; 
//	private static String DB_USER = "root";
//	private static String DB_PASSWORD ="";   
	
    public LoginServlet() {
        super();
    }
 
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		/**String driver = config.getInitParameter("driver");
        *String url = config.getInitParameter("url");
        */
		String username = config.getInitParameter("username");
		String pwd = config.getInitParameter("pwd");
		try {
			// Class.forName(driver).newInstance();
             
			conn = DBMgr.getConnection();
			System.out.println("Connected successful..");
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public void destroy() {
	
	}
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String pwd = request.getParameter("password");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from user where name = ? and pwd = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("登入成功");
				response.sendRedirect("success.html");
			}
			else {
				System.out.println("登入失败");
				RequestDispatcher rd = request.getRequestDispatcher("fail.html");
				rd.forward(request, response);
			}
			
		} catch (SQLException e) {
			System.out.println(e);
		}finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
 
}

