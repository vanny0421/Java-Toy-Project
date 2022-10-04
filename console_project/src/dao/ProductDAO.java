package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.ProductVO;
import vo.UserVO;

public class ProductDAO {
	Connection conn; // DB와 연결된 객체
	PreparedStatement pstm; // SQL문을 담는 객체
	ResultSet rs; // SQL문 결과를 담는 객체
	// 상품 찜하기
	// 상품 목록에 상품 고유 번호 넣기

	public void insertProductNum(int num) {
		
		String query = "select * from TBL_MARKET_SELL_PRODUCT where p_no=?";
		
		
		String sql = "insert into TBL_MARKET_SELL_PRODUCT values(?,0)";
		conn = DBConnecter.getConnection();
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, num);
			rs = pstm.executeQuery();
			if(!rs.next()) {
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1,num);
				pstm.executeUpdate();
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
	}
	
	//찜목록에 담기
	public void insertBookmark(int u_no, int pro_num) {
		boolean check = false;
		String sql = "insert into TBL_MARKET_BOOKMARK values(MARKET_BOOKMARK_SEQ.NEXTVAL,?,?)";
		conn= DBConnecter.getConnection();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, u_no);
			pstm.setInt(2, pro_num);
			pstm.executeUpdate();
				check = true;
			
			
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
//		return check;
	}
	
	// 찜하기 취소
	public boolean dropBookmark(int u_no, int pro_num) {
	      boolean check=false;
	      
	      String query ="SELECT * FROM TBL_MARKET_BOOKMARK WHERE B_U_NO = ? AND B_P_NO = ?";
	      try {
	         conn= DBConnecter.getConnection();
	         pstm = conn.prepareStatement(query);
	         
	         pstm.setInt(1,u_no);
	         pstm.setInt(2,pro_num);
	         rs = pstm.executeQuery();
	         System.out.println(u_no);
	         System.out.println(pro_num);
	         if(rs.next()) {
	            if(rs.getInt(2) == u_no && rs.getInt(3) == pro_num) {
	               query="DELETE FROM TBL_MARKET_BOOKMARK WHERE B_U_NO = ?AND B_P_NO = ?";
	               pstm = conn.prepareStatement(query);
	               pstm.setInt(1,u_no);
	               pstm.setInt(2,pro_num);
	               if (pstm.executeUpdate() == 1) {// executeUpdate()가 1일때 즉, 수정된 값이 하나가 있을때 트루
	                  check = true;
	            }
	            }
	         }
	      } catch (SQLException e) {
	         System.out.println("dropBookmark SQL문 오류");
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
	      return check;
	   }
	
	//찜 목록보기
	   public ArrayList<Integer> bookmarkP_number(int u_no) {
	        ProductVO vo = new ProductVO();
	        String query="SELECT B_P_NO FROM TBL_MARKET_BOOKMARK WHERE B_U_NO=?";
//	        int count = count(u_no);
	        ArrayList<Integer> pdNum = new ArrayList<Integer>();
	        
	        try {
	           conn = DBConnecter.getConnection();
	           pstm = conn.prepareStatement(query);
	           pstm.setInt(1,u_no);
	           rs = pstm.executeQuery();
	           
	           
	           while(rs.next()) {
//	        	   System.out.println(rs.getInt(1));
	        	   pdNum.add(rs.getInt(1));
	           }
	           
//	           if(rs.next()) {
//	        	   System.out.println(rs.getInt(1));
//	        	   for (int i = 0; i < count; i++) {
//	        		   vo.setB_p_no(rs.getInt(i));
//	        		   System.out.println(vo.getB_p_no());
//	        		   
//	        	   }
//	       		 }
	              
	        } catch (SQLException e) {
	           System.out.println("bookmarkP_number SQL문 오류");
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
	        return pdNum;
	        }
	   
	   public int count(int u_no) {
		   String sql = "SELECT COUNT(B_P_NO) FROM TBL_MARKET_BOOKMARK WHERE B_U_NO = ?";
		   int count = 0;
		   try {
	           conn = DBConnecter.getConnection();
	           pstm = conn.prepareStatement(sql);
	           pstm.setInt(1,u_no);
	           rs = pstm.executeQuery();
	           count = rs.getInt(1);
		   }catch (SQLException e) {
		           System.out.println("bookmarkP_number SQL문 오류");
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
		   return count;
	   }

	// 판매 내역
}
