package vo;

public class PurchasesVO {
   private int d_no;
   private String d_P_name;
   private String d_kinds;
   private String d_day;
   private int d_complete;
   
   public PurchasesVO() {;}

   public int getD_no() {
      return d_no;
   }

   public void setD_no(int d_no) {
      this.d_no = d_no;
   }

   public String getD_P_name() {
      return d_P_name;
   }

   public void setD_P_name(String d_P_name) {
      this.d_P_name = d_P_name;
   }

   public String getD_kinds() {
      return d_kinds;
   }

   public void setD_kinds(String d_kinds) {
      this.d_kinds = d_kinds;
   }

   public String getD_day() {
      return d_day;
   }

   public void setD_day(String d_day) {
      this.d_day = d_day;
   }

   public int getD_complete() {
      return d_complete;
   }

   public void setD_complete(int d_complete) {
      this.d_complete = d_complete;
   }
   
   
}