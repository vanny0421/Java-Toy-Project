package dao;

import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Crawling2 {
   
   public static WebDriver driver;
   public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
   public static final String WEB_DRIVER_PATH = "/Users/kimdongjin/Downloads/chromedriver";
   private String url;
   public static String searchText;
   public static int pro_num;
   public static int pdPrice3;
   
   public Crawling2(int p_no) {
      //드라이버 설정
      System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
      
      //화면에 브라우저 표시하지 않는 설정
      ChromeOptions options = new ChromeOptions();
      options.addArguments("headless");
      
      //driver에 옵션이 들어간 ChromeDriver 넣기.
      driver = new ChromeDriver(options);
      url = "https://www.daangn.com/articles/"+p_no;//당근
//      url = "https://m.joongna.com/home";//중고나라
      driver.get(url);
      try {Thread.sleep(1000);} catch (InterruptedException e) {;}
   }
   
   public void quit() {
      //드라이버 종료(close, quit)
      driver.close();
      driver.quit();
   }
   
   
   //검색 결과를 Scanner로 입력받기
   public void search() {
      
      //https://www.daangn.com/article/?? 상품번호
      
      
      try {
         driver.findElement(By.className("empty-result-message"));
         System.out.println("검색 결과가 없습니다.");
      }catch(NoSuchElementException e) {
    	  
    	 
    	 try {
			WebElement none = driver.findElement(By.id("no-article"));
			if(none.getSize() != null) {
				System.out.println(none.getText());
				return;
			}
		} catch (Exception e3) {;}
    	 
         //article-title 게시글 제목
         List<WebElement> title =  driver.findElements(By.className("article-title"));
         //article-content 게시글 내용
   //      List<WebElement> content =  driver.findElements(By.className("article-content"));
         //article-region-name 판매 지역
         List<WebElement> region =  driver.findElements(By.className("article-region-name"));
         //article-price 가격
         List<WebElement> price =  driver.findElements(By.className("article-price"));
         //flea-market-article 게시글 전체 태그
         List<WebElement> item = driver.findElements(By.className("flea-market-article"));
         
         
         WebElement we=  driver.findElement(By.id("article-title"));
         WebElement we2=  driver.findElement(By.id("article-price"));
         String[] pdPrice2 = we2.getText().split(",");
         String result = "";
         for (int j = 0; j < pdPrice2.length; j++) {
			result += pdPrice2[j];
		}
//         result.replace("원", "");
         try {
			pdPrice3 = Integer.parseInt(result.replace("원", ""));
		} catch (NumberFormatException e2) {;}
         
         
         for (int i = 0; i < title.size(); i++) {
            //게시글 제목 출력
            System.out.println(i + 1 + ". " + title.get(i).getText());
            //판매자 지역 출력
            System.out.println(region.get(i).getText());
            //가격 출력 만약 가격이 정해지지 않았다면 "-" 출력
            if(price.get(i).getText().equals("-")) {
               System.out.println("- (판매자 문의)");
            }else {
               System.out.println(price.get(i).getText());
            }
            //라인 구분
            System.out.println("============================");
         }
         
         
         int choicePd = 0;
         int choice = 0;

         
         
         try {Thread.sleep(3000);} catch (InterruptedException e1) {;}
         System.out.println("✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭");
         System.out.println(we.getText());
         System.out.println();
         System.out.println(we2.getText());
         System.out.println("판매자 " + driver.findElement(By.id("article-profile-right")).getText());
         System.out.println("");
         System.out.println(driver.findElement(By.id("article-detail")).getText());
         System.out.println("✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭");
   
      
   }
   
   }

}