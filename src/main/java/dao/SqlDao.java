package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DBconfig;
import dto.Customer;
import dto.LoginUser;

//アクセスロジック(DAO)
public class SqlDao {
		//DBconfig.propertiesのフルパス
		public final String file_path = "C:/pleiades/2022-06/workspace/ManagementSystem/DBconfig.properties";
		//DBconfigのオブジェクト生成
		DBconfig config = new DBconfig();
		
		//ログイン認証のメソッド
		public List<LoginUser> check(String user,String password)throws IOException{
			//DBconfig.propertiesの各値をlist形式で取得
			String[] DbInfo = config.getDBinfo(file_path);
			//DBconfig.propertiesのurlを取得
			String url = DbInfo[0];
			//DBconfig.propertiesのuserを取得
			String db_user_name = DbInfo[1];
			//DBconfig.propertiesのpasswordを取得
			String db_password = DbInfo[2];
			
			//login_user_tbのうち指定されたnameかつpasswordのものを検索するSQL文
			String sql = "SELECT * FROM login_user_tb WHERE name = ? and password = ?";
			
			//ログインユーザのオブジェクト生成（DTO）
			LoginUser login_user = new LoginUser();
			List<LoginUser> user_info = new ArrayList<LoginUser>();
			
			//JDBCドライバのロード
			try{
				  Class.forName("com.mysql.cj.jdbc.Driver");
		        } catch (ClassNotFoundException e){
		          throw new IllegalStateException("失敗しました。");
		     }
			
			//データベースへの接続
			try(Connection conn = DriverManager.getConnection(url,db_user_name,db_password)){
				PreparedStatement stmt = conn.prepareStatement(sql);
				//変数sqlの1番目の？に引数のuserをセットする
				stmt.setString(1,user);
				//変数sqlの2番目の？に引数のpasswordをセットする
				stmt.setString(2,password);
				//sqlを実行し該当するデータを格納
				ResultSet rs = stmt.executeQuery();
				
				if(rs.next()) {
					login_user.setId(rs.getInt("id"));
					login_user.setName(rs.getString("name"));
					login_user.setPassword(rs.getString("password"));
					user_info.add(login_user);
				} else {
					login_user.setName("No user");
					login_user.setPassword("Not match password");
					user_info.add(login_user);
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return user_info;
		}

		//顧客情報を取得するメソッド
		public List<Customer>get_customer_info() throws FileNotFoundException{
			//DBconfig.propertiesの各値をlist形式で取得
			String[] DbInfo = config.getDBinfo(file_path);
			//DBconfig.propertiesのurlを取得
			String url = DbInfo[0];
			//DBconfig.propertiesのuserを取得
			String db_user_name = DbInfo[1];
			//DBconfig.propertiesのpasswordを取得
			String db_password = DbInfo[2];
			
			String sql = "SELECT * FROM customer_tb";
			List <Customer> customer_info = new ArrayList<Customer>();
			Customer customer;
			
			//JDBCドライバのロード
			try{
				  Class.forName("com.mysql.cj.jdbc.Driver");
		        } catch (ClassNotFoundException e){
		          throw new IllegalStateException("失敗しました。");
		     }
			
			//DBへ接続
			try(Connection conn = DriverManager.getConnection(url,db_user_name,db_password)){
				
				//dtoインスタンスを作成
				customer = new Customer();
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					customer = new Customer();
					customer.setId(rs.getInt("id"));
					customer.setName(rs.getString("name"));
					customer.setAddress(rs.getString("address"));
					customer.setTel_number(rs.getString("tel_number"));
					customer_info.add(customer);					
				}
			}catch(SQLException e){
				e.printStackTrace();
			}			
			return customer_info;
		}
		
		public void insert_customer_info(String name, String address,String tel)throws FileNotFoundException {
			//DBconfig.propertiesの各値をlist形式で取得
			String[] DbInfo = config.getDBinfo(file_path);
			//DBconfig.propertiesのurlを取得
			String url = DbInfo[0];
			//DBconfig.propertiesのuserを取得
			String db_user_name = DbInfo[1];
			//DBconfig.propertiesのpasswordを取得
			String db_password = DbInfo[2];
			
			//フォームに入力された情報をSQL文へ流す
			String sql = "INSERT INTO customer_tb(name,address,tel_number)VALUES(?,?,?)";
			//JDBCドライバのロード
			try{
				  Class.forName("com.mysql.cj.jdbc.Driver");
		        } catch (ClassNotFoundException e){
		          throw new IllegalStateException("失敗しました。");
		     }
			try(Connection conn = DriverManager.getConnection(url,db_user_name,db_password)){				
				conn.setAutoCommit(false);
				try(PreparedStatement stmt = conn.prepareStatement(sql)){
					//変数sqlの1番目の？に第1引数のnameをセット
					stmt.setString(1, name);
					//変数sqlの2番目の？に第2引数のaddressをセット
					stmt.setString(2, address);
					//変数sqlの3番目の？に第3引数のtelをセット
					stmt.setString(3, tel);
					stmt.executeUpdate();
					conn.commit();
				}catch(Exception e) {
					conn.rollback();
					System.out.println("データの処理が正常に終了しませんでした");
					throw e;
				}
			}catch(SQLException e1) {
				System.out.println("SQLの例外が発生しました");
				e1.printStackTrace();
			}
		}
		
		public List<Customer> get_specific_customer_info(String customer_id) throws FileNotFoundException{
			//DBconfig.propertiesの各値をlist形式で取得
			String[] DbInfo = config.getDBinfo(file_path);
			//DBconfig.propertiesのurlを取得
			String url = DbInfo[0];
			//DBconfig.propertiesのuserを取得
			String db_user_name = DbInfo[1];
			//DBconfig.propertiesのpasswordを取得
			String db_password = DbInfo[2];
			
			//SQL文のテンプレートを作成
			String sql = "SELECT * FROM customer_tb WHERE id = ?";
			
			//dtoインスタンスを作成
			Customer customer = new Customer();
			
			List<Customer> specific_customer = new ArrayList<>();
			
			//JDBCドライバのロード
			try{
				  Class.forName("com.mysql.cj.jdbc.Driver");
		        } catch (ClassNotFoundException e){
		          throw new IllegalStateException("失敗しました。");
		     }
			try(Connection conn = DriverManager.getConnection(url,db_user_name,db_password)){
				PreparedStatement stmt = conn.prepareStatement(sql);
				//変数sqlの1番目の？に引数のuserをセットする
				stmt.setString(1,customer_id);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {				
					customer.setId(rs.getInt("id"));
					customer.setName(rs.getString("name"));				
					customer.setAddress(rs.getString("address"));
					customer.setTel_number(rs.getString("tel_number"));
					specific_customer.add(customer);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return specific_customer;
		}
		
		public void update_customer_data(String name,String address,String tel,String id)throws FileNotFoundException{
			//DBconfig.propertiesの各値をlist形式で取得
			String[] DbInfo = config.getDBinfo(file_path);
			//DBconfig.propertiesのurlを取得
			String url = DbInfo[0];
			//DBconfig.propertiesのuserを取得
			String db_user_name = DbInfo[1];
			//DBconfig.propertiesのpasswordを取得
			String db_password = DbInfo[2];
			
			String sql = "UPDATE customer_tb SET name= ?,address =?,tel_number=? WHERE id=?";
								
			//JDBCドライバのロード
			try{
				  Class.forName("com.mysql.cj.jdbc.Driver");
		        } catch (ClassNotFoundException e){
		          throw new IllegalStateException("失敗しました。");
		     }
			try(Connection conn = DriverManager.getConnection(url,db_user_name,db_password)){
				conn.setAutoCommit(false);
				try(PreparedStatement stmt = conn.prepareStatement(sql)){
					stmt.setString(1, name);
					stmt.setString(2, address);
					stmt.setString(3, tel);
					stmt.setString(4, id);
					stmt.executeUpdate();
					conn.commit();
				}catch(Exception e) {
					conn.rollback();
					System.out.println("データ処理が正常に終了しませんでした");
					e.printStackTrace();
					throw e;
				}
			}catch(SQLException e1) {
				e1.printStackTrace();
				System.out.println("SQLに例外が生じました");
				e1.printStackTrace();
			}
			
		}
		public void delete_customer_list(String id) throws FileNotFoundException{
			//DBにログインするためのデータ取得
			String[] DbInfo =  config.getDBinfo(file_path);
			String url = DbInfo[0];
			String db_user_name = DbInfo[1];
			String db_password = DbInfo[2];
			
			//該当のidの行を削除するSQL文を作成
			String sql = "DELETE FROM customer_tb WHERE id=?";
			
			//JDBCドライバのロード
			try{
				Class.forName("com.mysql.cj.jdbc.Driver");
			}catch(ClassNotFoundException e) {
				throw new IllegalStateException("失敗しました。");
			}
			
			try(Connection conn = DriverManager.getConnection(url,db_user_name,db_password)){
				conn.setAutoCommit(false);
				try(PreparedStatement stmt = conn.prepareStatement(sql)){
					stmt.setString(1, id);
					stmt.executeUpdate();
					conn.commit();
				}catch(Exception e) {
					conn.rollback();
					System.out.println("データ処理が正常に終了しませんでした");
					e.printStackTrace();
					throw e;
				}
			}catch(SQLException e1) {
				e1.printStackTrace();
				System.out.println("SQLに例外が生じました");
				e1.printStackTrace();
			}
		}
}

