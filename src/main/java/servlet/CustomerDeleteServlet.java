package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SqlDao;
import dto.Customer;


@WebServlet("/CustomerDeleteServlet")
public class CustomerDeleteServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String  id = request.getParameter("id");		 
		 SqlDao sql = new SqlDao();	
		 sql.delete_customer_list(id);
		 
		 List<Customer> customer_data = new ArrayList<>();
		 customer_data = sql.get_customer_info();
		 //requestオブジェクトに顧客情報を持たせる
		 request.setAttribute("customer", customer_data);
		 //顧客画面に遷移
		 RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/customer_list.jsp");
		 dispatcher.forward(request, response);
	 }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		}
	}
