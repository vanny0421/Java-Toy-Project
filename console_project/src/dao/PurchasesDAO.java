package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.PurchasesVO;
import vo.UserVO;

public class PurchasesDAO {
   Connection conn; // DB와 연결된 객체
   PreparedStatement pstm; // SQL문을 담는 객체
   ResultSet rs; // SQL문 결과를 담는 객체
   boolean check  =false;
   // 구매 내역
   public ArrayList<String> purchaseDetails(int u_no) {
      
      ArrayList<String>  list = new ArrayList<>();
      String query = "SELECT * FROM TBL_MARKET_ME_BUY_LIST S JOIN TBL_MARKET_TRADER T ON T.D_NO = S.D_NO WHERE T.U_NO = ?";

      try {
         conn = DBConnecter.getConnection();
         pstm = conn.prepareStatement(query);
         pstm.setInt(1,UserDAO.session);
         rs = pstm.executeQuery();

         while(rs.next()) {
            list.add(rs.getInt(1)+"/"+rs.getString(2)+"/"+rs.getString(3)+"/"+rs.getString(4)+"/"+rs.getInt(5));
//            vo.setD_no(rs.getInt(1));
//            vo.setD_P_name(rs.getString(2));
//            vo.setD_kinds(rs.getString(3));
//            vo.setD_day(rs.getString(4));
//            vo.setD_complete(rs.getInt(5));
            
            
         
         }

      } catch (SQLException e) {
         System.out.println("viewProfile SQL문 오류");
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
            if (pstm != null) {
               pstm.close();
            }
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
         }
      }
      return list;
   }

}
// 구매 완료 시 상품 고유번호 등록