package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import org.json.simple.JSONObject;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import vo.UserVO;

/* 3조 중고 거래 사이트

김이영 : 상세 기획안 작성, 팀원 관리

민석홍 : DB 설계
김동진 : 상품 검색 크롤링
임재훈 : 패키지, 클래스 분리
전진주 : JDBC 테스트
정영재 : 등급 상세 기획*/

/*유저테이블(로그인.회원가입 정보, 포인트로 등급설정, 동네설정 등)

상품등록테이블
상품 검색
↪ 시세, 최저가 ~ 최고가

상품 분류
↪ 관심목록(찜하기)

상품 등록
↪ 

평점 테이블
↪ 판매후기, 거래후기 신고
↪ 글 도배하는사람들 비매너 신고
　게시글 정지 / 글 숨기기
  예) 1일, 3일, 일주일, 한달

상품 거래 테이블
↪ 판매내역 구매내역

결제 테이블
↪ 계좌(?)

채팅 테이블
↪ 판매 레이아웃, */

public class UserDAO {

   public static int session;
   // public static int statusSession;
   public static String number;
   public static int tempU_no;
   public static int userNumber;

   Connection conn; // DB와 연결된 객체
   PreparedStatement pstm; // SQL문을 담는 객체
   ResultSet rs; // SQL문 결과를 담는 객체

   // 회원가입o, 로그인o, 탈퇴o, 복구o, 로그아웃o
   // 프로필 보기
   // 회원정보
   // 프로필 수정

   // 판매내역
   // 판매중
   // 거래완료

   // 관심목록
   // 상품 찜하기
   // 찜하기 취소 회원 넘버에 찜하면 고유번호 넘기기

   // 간편결제
   // 계좌번호 불러와서 판매자에게 입금
   // 구매자 송금
   // 회사 계좌
   // 입금, 출금

   // 원하는 상품 보기

   //회원가입
   public void join(UserVO user) {
      String query = "INSERT INTO TBL_MARKET_USER(u_no, u_nickname, u_email, u_phoneNum,  u_birthDate, u_city, u_bankName, u_accountNum) VALUES(MARKET_USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
      try {
         conn = DBConnecter.getConnection();
         pstm = conn.prepareStatement(query);

         pstm.setString(1, user.getU_nickName()); // nick
         pstm.setString(2, user.getU_email()); // email
         pstm.setString(3, user.getU_phoneNum());
         pstm.setString(4, user.getU_birthDate());
         pstm.setString(5, user.getU_city());
         pstm.setString(6, user.getU_bankName());
         pstm.setString(7, user.getU_accountNum());

         pstm.executeUpdate();

      } catch (SQLException e) {
         System.out.println("join SQL문 오류");
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
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
   } // join

   public void logout() {
      session = 0;
   }

   public boolean checkPhoneNum(String phoneNum) { // 계정확인
      String query = "SELECT U_NO FROM TBL_MARKET_USER WHERE U_PHONENUM = ?";
      boolean check = false;
      try {
         conn = DBConnecter.getConnection();
         pstm = conn.prepareStatement(query);
         pstm.setString(1, phoneNum);
         rs = pstm.executeQuery();

         if(rs.next()) {
            session = rs.getInt(1);
            check= true;
         }
        
      } catch (SQLException e) {
         System.out.println("checkId SQL문 오류");
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

   // 회원 탈퇴자와 일반 회원의 구분
   public boolean checkSignOut(String phoneNum) {
      String query = "SELECT U_NONREGI FROM TBL_MARKET_USER WHERE U_PHONENUM = ?";
      boolean check = false;
      try {
         conn = DBConnecter.getConnection();
         pstm = conn.prepareStatement(query);
         pstm.setString(1, phoneNum);
         rs = pstm.executeQuery();

         if (rs.next()) { // PHONENUM을 받아서 쿼리문에 넣고 뽑아낸 PLUG값이 1이면 탈퇴자
            if (rs.getInt(1) == 1) {
               check = true;
            }
         }

      } catch (SQLException e) {
         System.out.println("checkSignOut SQL문 오류");
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
      return check; // PHONENUM을 받아서 쿼리문에 넣고 뽑아낸 PLUG값이 1이 아니면 일반 회원
   } // checkSignOut

   // 회원 탈퇴
   public boolean drop(String phoneNumber) {
      // SELECT PW FROM TBL_USER WHERE USERNUMBER = ?
      String query = "SELECT U_NONREGI  FROM TBL_MARKET_USER WHERE u_no = ?";
      boolean check = false;
      try {
         conn = DBConnecter.getConnection();
         pstm = conn.prepareStatement(query);
         pstm.setInt(1, session);
         rs = pstm.executeQuery();

         if (rs.next()) {
            if (rs.getInt(1) == 0) {
               query = "UPDATE TBL_MARKET_USER SET U_NONREGI = 1 WHERE U_NO =?";
               conn = DBConnecter.getConnection();
               pstm = conn.prepareStatement(query);
               pstm.setInt(1, session);
               if (pstm.executeUpdate() == 1) {
                  check = true;
               }
            }
         }
      } catch (SQLException e) {
         System.out.println("delete SQL문 오류");
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
   } // delete

   // 탈퇴한 회원 복구
   public boolean restore(String phoneNumber) {
      String query = "SELECT U_NONREGI  FROM TBL_MARKET_USER WHERE U_PHONENUM =?";
      boolean check = false;
      try {
         conn = DBConnecter.getConnection();
         pstm = conn.prepareStatement(query);
         pstm.setString(1, phoneNumber);

         // executeQuery()는 null이 나오지 않는다.
         rs = pstm.executeQuery();
         // 따라서 검색 결과가 없다면 rs.next()가 false이기 때문에
         // rs.next()로 판단해야 한다.
         if (rs.next()) {
            if (rs.getInt(1) == 1) {
               query = "UPDATE TBL_MARKET_USER SET U_NONREGI  = 0 WHERE U_PHONENUM =?";
               conn = DBConnecter.getConnection();
               pstm = conn.prepareStatement(query);
               pstm.setString(1, phoneNumber);
               if (pstm.executeUpdate() == 1) {
                  check = true;
               }
            }
         }
      } catch (SQLException e) {
         System.out.println("restore SQL문 오류");
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
   } // restore

   // 핸드폰번호로 랜덤한 인증번호 전송, view에서 인증번호를 입력 받고 인증이 성공하면 다음 단계 넘어가도록 설계
   public void sendNumber(String phoneNumber) {
      String api_key = "NCS5XBPDPNJULWVY";
      String api_secret = "FV8DCBHL1HSDMNVO02YK00651EOQ8V1L";
      Message coolsms = new Message(api_key, api_secret);
      String data = "0123456789";
      String number = "";
      Random r = new Random();

      for (int i = 0; i < 6; i++) {
         number += data.charAt(r.nextInt(data.length()));
      }
      UserDAO.number = number;
      // 4 params(to, from, type, text) are mandatory. must be filled
      HashMap<String, String> params = new HashMap<String, String>();
      Scanner sc = new Scanner(System.in);
      params.put("to", "01091685043");// phoneNumber
      params.put("from", "01091685043");
      params.put("type", "SMS");
      params.put("text", "본인인증번호는 " + number + "입니다. 정확히 입력해주세요.");
      params.put("app_version", "test app 1.2"); // application name and version

      try {
         JSONObject obj = (JSONObject) coolsms.send(params);
         System.out.println(obj.toString());
      } catch (CoolsmsException e) {
         System.out.println(e.getMessage());
         System.out.println(e.getCode());
      }
   } // id

   // 인증번호 확인
   public boolean checkVCode(String number) {
      boolean check = false;

      if (UserDAO.number.equals(number)) {
         check = true;
      }
      return check;
   }

   // 회원 정보 수정
   // 회원 정보 수정
   public boolean changeInfo(UserVO vo) {
         String query = "UPDATE TBL_MARKET_USER SET U_NICKNAME=?, U_EMAIL=?, U_PHONENUM=?, U_CITY=?, U_ACCOUNTNUM=? WHERE U_NO =?";
         boolean check = false;

         try {
            conn = DBConnecter.getConnection();
            pstm = conn.prepareStatement(query);

               conn = DBConnecter.getConnection();
               pstm = conn.prepareStatement(query);
               pstm.setString(1, vo.getU_nickName());
               pstm.setString(2, vo.getU_email());
               pstm.setString(3, vo.getU_phoneNum());
               pstm.setString(4, vo.getU_city());
               pstm.setString(5, vo.getU_accountNum());
               pstm.setInt(6, session);

               if (pstm.executeUpdate() == 1) {
                  check = true;
            }
         } catch (SQLException e) {
            System.out.println("changeInfo SQL문 오류");
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
      } // changeNickName

   //프로필보기
   public UserVO viewProfile() {
       UserVO vo =new UserVO();
       String query = "SELECT * FROM TBL_MARKET_USER WHERE U_NO =?";

       
       try {
          conn = DBConnecter.getConnection();
          pstm = conn.prepareStatement(query);
          pstm.setInt(1,session);
          // executeQuery()는 null이 나오지 않는다.
          rs = pstm.executeQuery();
          // 따라서 검색 결과가 없다면 rs.next()가 false이기 때문에
          // rs.next()로 판단해야 한다.
          if(rs.next()) {
             vo.setU_no(rs.getInt(1));
             vo.setU_nickName(rs.getString(2));
             vo.setU_email(rs.getString(3));
             vo.setU_phoneNum(rs.getString(4));
             vo.setU_birthDate(rs.getString(5));
             vo.setU_grade(rs.getInt(6));
             vo.setU_city(rs.getString(7));
             vo.setU_bankName(rs.getString(8));
             vo.setU_accountNum(rs.getString(9));
             vo.setU_totalPrice(rs.getInt(10));
             vo.setU_nonRegi(rs.getInt(11));
            
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
       return vo;
    }
   

   public String getNickName() {
	   String nickName = "";
	   String sql = "SELECT U_NICKNAME FROM TBL_MARKET_USER WHERE U_NO = ?";
	   
	   try {
	          conn = DBConnecter.getConnection();
	          pstm = conn.prepareStatement(sql);
	          pstm.setInt(1,session);
	          
	          rs = pstm.executeQuery();
	          
	          if(rs.next()){
	        	nickName = rs.getString(1);
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
	   return nickName;
   }

} // class