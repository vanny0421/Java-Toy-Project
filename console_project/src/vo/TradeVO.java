package vo;

public class TradeVO {
   private int p_price;
   private String sell_user_nickname;
   private String buy_user_nickname;
   private String p_name;
   private String time;
   private String comp;

   public String getComp() {
      return comp;
   }
   public void setComp(String comp) {
      this.comp = comp;
   }
   public int getP_price() {
      return p_price;
   }
   public void setP_price(int p_price) {
      this.p_price = p_price;
   }
   public String getSell_user_nickname() {
      return sell_user_nickname;
   }
   public void setSell_user_nickname(String sell_user_nickname) {
      this.sell_user_nickname = sell_user_nickname;
   }
   public String getBuy_user_nickname() {
      return buy_user_nickname;
   }
   public void setBuy_user_nickname(String buy_user_nickname) {
      this.buy_user_nickname = buy_user_nickname;
   }
   public String getP_name() {
      return p_name;
   }
   public void setP_name(String p_name) {
      this.p_name = p_name;
   }
   public String getTime() {
      return time;
   }
   public void setTime(String time) {
      this.time = time;
   }
}