package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.TradeVO;

public class TradeDAO {
	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;
	
	public int post_loading() {
		int flag = 0;
		try {
			System.out.println("배송 준비중입니다.");
			Thread.sleep(5000);
			System.out.println("배송중입니다");
			Thread.sleep(5000);
			System.out.println("배송완료");
			flag = 1;
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public int max_index() {
		String query = "SELECT D_NO FROM TBL_MARKET_TRADER ORDER BY D_NO DESC";
		int index = 0;
		conn = DBConnecter.getConnection();
		try {
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();
			if(rs.next()) {
				index = rs.getInt(1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstm != null) {
					pstm.close();
				}
				if(conn != null) {
					conn.close();
				}
			}catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return index;
	}
	
	public void purchase(TradeVO trade) {
		int i = max_index() + 1;
		String query = "INSERT INTO TBL_MARKET_ME_BUY_LIST(D_NO,D_P_NAME,D_COMPLETE)"
				+ "VALUES(?, ?, ?)";
		
		conn = DBConnecter.getConnection();
		try {
			pstm = conn.prepareStatement(query);
			
			pstm.setInt(1, i);
			pstm.setString(2, trade.getP_name());
			pstm.setInt(3, post_loading());
			pstm.executeUpdate();
			//DB 트렌젝션으로 처리 필요
			query="UPDATE TBL_MARKET_USER SET U_BALANCE = U_BALANCE - ? WHERE U_NO = ?";
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, trade.getP_price());
			pstm.setInt(2, UserDAO.session);
			pstm.executeUpdate();
			
			query = "INSERT INTO TBL_MARKET_TRADER(D_NO,D_SELLER,D_BUYER,U_NO)"
		               + "VALUES(?,?,?,?)";
			pstm = conn.prepareStatement(query);
	        pstm.setInt(1, i); //D_NO
	        pstm.setString(2,trade.getSell_user_nickname());//판매자
	        pstm.setString(3,trade.getBuy_user_nickname());//구매자(나)
	        pstm.setInt(4,UserDAO.session);//U_NO
	        pstm.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstm != null) {
					pstm.close();
				}
				if(conn != null) {
					conn.close();
				}
			}catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	public void get_trade_list() {
		String query = "SELECT T1.D_NO,T1.D_SELLER,T1.D_BUYER,T2.D_P_NAME,T2.D_DAY,T2.D_COMPLETE "
				+ "FROM TBL_MARKET_TRADER T1,TBL_MARKET_ME_BUY_LIST T2 "
				+ "WHERE T1.D_NO = T2.D_NO ORDER BY T1.D_NO DESC";
		
		conn = DBConnecter.getConnection();
		try {
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
	            System.out.print(rs.getInt(1)+" ");
	            System.out.print(rs.getString(2)+" ");
	            System.out.print(rs.getString(3)+" ");
	            System.out.print(rs.getString(4)+" ");
	            System.out.print(rs.getString(5)+" ");
	            System.out.print(rs.getInt(6)+" ");
	            System.out.println("");
	            System.out.println("");
	         }
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstm != null) {
					pstm.close();
				}
				if(conn != null) {
					conn.close();
				}
			}catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
}//class
