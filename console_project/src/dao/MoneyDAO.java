package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.UserVO;

public class MoneyDAO {

   Connection conn; // DB와 연결된 객체
   PreparedStatement pstm; // SQL문을 담는 객체
   ResultSet rs; // SQL문 결과를 담는 객체

   UserVO vo = new UserVO();
   UserDAO dao = new UserDAO();
   int dMoney = 0; // 입금 받을 회원의 totalprice

   // 송금(송금액)
   public boolean sendMoney(int money, int u_no) {
      vo = dao.viewProfile();
      if (money <= 0 || vo.getU_totalPrice() - money < 0) {
         return false;
      } else {
         String query = "UPDATE TBL_MARKET_USER SET U_BALANCE = ? WHERE U_NO =?";

         try {
            conn = DBConnecter.getConnection();
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, vo.getU_totalPrice() - money); // 입력받은 금액을 totalprice에서 뺀 값을 넘긴다.
            pstm.setInt(2, UserDAO.session); // 로그인 한 회원의 유저 넘버
            pstm.executeUpdate();
            
//            query = "SELECT U_BALANCE FROM TBL_MARKET_USER WHERE U_NO =?";
//            
//            conn = DBConnecter.getConnection();
//            pstm = conn.prepareStatement(query);
//            pstm.setInt(1, u_no); // 입금 받을 유저의 회원번호 입력 받아서 넣음
//            rs = pstm.executeQuery();
//            
//            rs.next();
//            dMoney = rs.getInt(1);
            
            query = "UPDATE TBL_MARKET_USER SET U_BALANCE = U_BALANCE + ? WHERE U_NO =?";
            conn = DBConnecter.getConnection();
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, money);
            pstm.setInt(2, u_no);
            pstm.executeUpdate();     // 입금 받을 사람에게 돈 전송 완료
            
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      return true;
   }

   // 출금
   public boolean withdraw(int money) {
      vo = dao.viewProfile();
      if (vo.getU_totalPrice() - money < 0) {
         return false;
      } // 로그인한 유저의 totalPrice에 있는 금액보다 출금액이 크다면 출금 실패

      else {
         String query = "UPDATE TBL_MARKET_USER SET U_BALANCE = ? WHERE U_NO =?";
         try {
            conn = DBConnecter.getConnection();
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, vo.getU_totalPrice() - money); // 입력받은 금액을 totalprice에서 뺀 값을 넘긴다.
            pstm.setInt(2, UserDAO.session); // 로그인 한 회원의 유저 넘버
            pstm.executeUpdate();
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         return true;
      }
   }

   
   // 입금
      public boolean deposit(int money) {
         vo = dao.viewProfile();
         if (money <= 0) {
            return false;
         } // 입금액이 0보다 작으면 입금 실패
         else {
            String query = "UPDATE TBL_MARKET_USER SET U_BALANCE = ? WHERE U_NO =?";
            try {
               conn = DBConnecter.getConnection();
               pstm = conn.prepareStatement(query);
               pstm.setInt(1, vo.getU_totalPrice() + money); // 입력받은 금액을 U_BALANCE에서 더한 값을 넘긴다.
               pstm.setInt(2, UserDAO.session); // 로그인 한 회원의 유저 넘버
               pstm.executeUpdate();
            } catch (SQLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            return true;
         }
      }
      
}