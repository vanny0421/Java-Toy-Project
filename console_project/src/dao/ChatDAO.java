package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatDAO {
   Connection conn; // DB와 연결된 객체
   PreparedStatement pstm; // SQL문을 담는 객체
   ResultSet rs; // SQL문 결과를 담는 객체
   
   //채팅 보내기
   
   public void sendChat(int sender, String recipient, String content, int p_no ) {
      String sql = "insert into TBL_MARKET_CHATTING VALUES(MARKET_CHAT_SEQ.NEXTVAL , ?, ?, ?, ?)";
      
      try {
         conn = DBConnecter.getConnection();
         pstm=conn.prepareStatement(sql);
         pstm.setInt(1, sender);
         pstm.setString(2, recipient);
         pstm.setString(3, content);
         pstm.setInt(4, p_no);
         
         pstm.executeUpdate();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }finally {
            try {
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
   }
   
   
   
   //채팅 받기
   public ArrayList<String> viewChat(String phoneNum) {//로그인된 핸드폰 번호
      ArrayList<String>  list = new ArrayList<>();
      String sql = "select * from TBL_MARKET_CHATTING c JOIN TBL_MARKET_USER u  ON c.CHAT_SENDER=u.U_NO where chat_recipient = ?";
      
      try {
         conn = DBConnecter.getConnection();
         pstm=conn.prepareStatement(sql);
         pstm.setString(1, phoneNum);
         rs = pstm.executeQuery();
         
         while(rs.next()) {
            list.add(rs.getInt(5)+"/"+rs.getString(4)+"/"+rs.getString(7));
         }
         
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }finally {
            try {
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