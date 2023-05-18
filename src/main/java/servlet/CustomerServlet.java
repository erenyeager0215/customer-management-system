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


@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet{
	//aタグでdoGetの呼び出し
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		   SqlDao sql = new SqlDao(); 
		   //顧客情報を作成
		   List<Customer> customer_data = new ArrayList<Customer>();
		   //顧客情報を取得するメソッドの呼び出し
		   customer_data = sql.get_customer_info();
		   //requestオブジェクトに顧客情報を持たせる
		   request.setAttribute("customer", customer_data);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/customer_list.jsp");
		dispatcher.forward(request, response);
	}
}
