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

@WebServlet("/CustomerDetailServlet")
public class CustomerDetailServlet extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		 SqlDao sql = new SqlDao();
			List<Customer> specific_customer_data = new ArrayList<Customer>();
		   //顧客情報を取得するメソッドの呼び出し
		   specific_customer_data = sql.get_specific_customer_info(id);
		   //requestオブジェクトに顧客情報を持たせる
		   request.setAttribute("specific_customer_data", specific_customer_data);		   
		   //顧客情報画面に遷移
		   RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/customer_detail.jsp");
		   dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		 //文字コードの設定
		   response.setContentType("text/html; charset=UTF-8");
		   request.setCharacterEncoding("UTF-8");
		//入力情報を取得   
		   String customer_name = request.getParameter("customer_name");
		   String customer_address = request.getParameter("customer_address");
		   String customer_tel = request.getParameter("customer_tel");
		   String id = request.getParameter("id");
		   List<Customer>customer_info = new ArrayList<Customer>();
		   SqlDao sql = new SqlDao();
		   //既存のデータを上書き
		   sql.update_customer_data(customer_name,customer_address,customer_tel,id);		   
		  
		   //すべての顧客情報を取得し、requestインスタンスに情報を流す
		   List<Customer> customer_data = new ArrayList<Customer>();
		   customer_data = sql.get_customer_info();
		   request.setAttribute("customer", customer_data);
		   
		   //顧客画面に遷移
		   RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/customer_list.jsp");
		   dispatcher.forward(request,response);
	}
}
