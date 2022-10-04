package vo;

public class UserVO {
   /*유저 테이블*/
   private int u_no;                     // 회원번호 pk
   private String u_nickName;          // 닉네임
   private String u_email;             // 메일주소
   private String u_phoneNum;         // 핸드폰 번호
   private String u_birthDate;         // 생년월일
   private int u_grade;                // 등급
   private String u_city;                // 거주지역
   private String u_bankName;         // 은행
   private String u_accountNum;       // 계좌번호
   private int u_totalPrice;             // 지갑
   private int u_nonRegi;   // 회원 탈퇴시 1

   public UserVO() {;}

   public int getU_no() {
      return u_no;
   }

   public void setU_no(int u_no) {
      this.u_no = u_no;
   }

   public String getU_nickName() {
      return u_nickName;
   }

   public void setU_nickName(String u_nickName) {
      this.u_nickName = u_nickName;
   }

   public String getU_email() {
      return u_email;
   }

   public void setU_email(String u_email) {
      this.u_email = u_email;
   }

   public String getU_phoneNum() {
      return u_phoneNum;
   }

   public void setU_phoneNum(String u_phoneNum) {
      this.u_phoneNum = u_phoneNum;
   }

   public String getU_birthDate() {
      return u_birthDate;
   }

   public void setU_birthDate(String u_birthDate) {
      this.u_birthDate = u_birthDate;
   }

   public int getU_grade() {
      return u_grade;
   }

   public void setU_grade(int u_grade) {
      this.u_grade = u_grade;
   }

   public String getU_city() {
      return u_city;
   }

   public void setU_city(String u_city) {
      this.u_city = u_city;
   }

   public String getU_bankName() {
      return u_bankName;
   }

   public void setU_bankName(String u_bankName) {
      this.u_bankName = u_bankName;
   }

   public String getU_accountNum() {
      return u_accountNum;
   }

   public void setU_accountNum(String u_accountNum) {
      this.u_accountNum = u_accountNum;
   }

   public int getU_totalPrice() {
      return u_totalPrice;
   }

   public void setU_totalPrice(int u_totalPrice) {
      this.u_totalPrice = u_totalPrice;
   }

   public int getU_nonRegi() {
      return u_nonRegi;
   }

   public void setU_nonRegi(int u_nonRegi) {
      this.u_nonRegi = u_nonRegi;
   }

   
   
   
   
}