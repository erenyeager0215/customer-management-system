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
import dto.LoginUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   //ログイン画面の表示処理
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}
	
   //ログイン認証のメインロジック
   protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException{
	   //文字コードの設定
	   response.setContentType("text/html; charset=UTF-8");
	   request.setCharacterEncoding("UTF-8");
	   
	   //ログイン画面で入力された値を取得
	   String user = request.getParameter("user_name");
	   String password = request.getParameter("password");
	   
	   SqlDao sql = new SqlDao();
	   List <LoginUser> login = sql.check(user,password);
	   
	   String login_user = login.get(0).getName();
	   String login_pass = login.get(0).getPassword();
	   
	   if(user.equals(login_user) && password.equals(login_pass)) {
		   //ログイン成功→次の画面へ
		   //顧客情報を作成
		   List<Customer> customer_data = new ArrayList<Customer>();
		   //顧客情報を取得するメソッドの呼び出し
		   customer_data = sql.get_customer_info();
		   //requestオブジェクトに顧客情報を持たせる
		   request.setAttribute("customer", customer_data);
		   //顧客画面に遷移
		   RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/customer_list.jsp");
		   dispatcher.forward(request, response);
	   }else {
		   //ログイン失敗→ログイン画面へ戻る
		   RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
		   dispatcher.forward(request, response);
	   }
   }
	
}
