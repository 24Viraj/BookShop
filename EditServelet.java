import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditServelet extends HttpServlet {
	private static final String query = "update book1 set booknam=?, bookedi=?, bookpri=? where id = ?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		int id = Integer.parseInt(req .getParameter("id"));
		String bookName=req.getParameter("bookName");
		String bookEdition=req.getParameter("bookEdition");
		float bookPrice=Float.parseFloat(req.getParameter("bookPrice"));
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException cfe) {
			cfe.printStackTrace();
		}
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaadv","root","root");
				PreparedStatement ps = con.prepareStatement(query); ){
			    ps.setString(1,bookName);
			    ps.setString(2,bookEdition);
			    ps.setFloat(3,bookPrice);
			    ps.setInt(4, id);
			    int count=ps.executeUpdate();
			    if(count==1) {
			    	pw.println("<h1>Data Edited</h1> ");
			    	}else {
			    		pw.println("<h1>Something went wrong</h1> ");
			    	}
		}catch (SQLException se) {
	        se.printStackTrace();
	        pw.println("<h1>" + se.getMessage() + "</h2>");
	    } catch (Exception e) {
	        e.printStackTrace();
	        pw.println("<h1>" + e.getMessage() + "</h2>");
	    }
		pw.println("<a href='index.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='booklist'>Book list</a>");

	}
	@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			doGet(req,res);
		}
}
