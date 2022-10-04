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

import projectView.projectForm;
import vo.TradeVO;

public class Crawling {
   public static String text_copy;//사용자가 선택한 게시글의 주소를 넣는 변수
   public static WebDriver driver;
   public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
   public static final String WEB_DRIVER_PATH = "/Users/kimdongjin/eclipse/chromedriver";
   private String url;
   public static String searchText;//
   public static int pro_num;//게시글 주소 끝 상품번호를 넣음
   public static int count;//메소드 실행횟수 체크
   public static int pdPrice;//상품가격
   public static int number2;//이전을 선택하면 그 전에 담긴 choicePd에 -1이 담기는데 전역변수로 마지막에 선택한 값이 담기게 한다.
   public static String noResult;
   
   public Crawling() {
      //드라이버 설정
      System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
      
      //화면에 브라우저 표시하지 않는 설정
      ChromeOptions options = new ChromeOptions();
//      options.addArguments("headless");
      
      //driver에 옵션이 들어간 ChromeDriver 넣기.
      driver = new ChromeDriver(options);
      url = "https://www.daangn.com/";//당근
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
   public void search(String text) {
	   projectForm pf = new projectForm();
	   
	   Scanner sc = new Scanner(System.in);
	   int i = 0;
	   noResult = "exist";
      if(text.equals("")) {
         searchText="";
         System.out.print("검색어를 입력해주세요 : ");
         }
//      String remember = "";
      //https://www.daangn.com/article/?? 상품번호
      
      
      //사이트 검색바 선택
      WebElement search = driver.findElement(By.className("fixed-search-input"));//당근
//      WebElement search = driver.findElement(By.className("Home_searchBar__JVWAN"));//중고나라
      
      //사용자가 입력한 값을 검색창에 넣어준다.
      if(text.equals("")) {
         text = sc.nextLine();
      }
      
//      remember = text;
//      System.out.println("text : " + text);
      searchText = text;
      
      //text값을 검색어에 입력
         search.sendKeys(text);
         try {Thread.sleep(1000);} catch (InterruptedException e) {;}
      
      //입력한 값으로 검색
         search.sendKeys(Keys.RETURN);
         try {Thread.sleep(1000);} catch (InterruptedException e) {;}
//      driver.get("https://www.daangn.com/article/상품번호");
      
      //empty-result-message 검색한 결과가 없을 때
      try {
         driver.findElement(By.className("empty-result-message"));
         System.out.println("검색 결과가 없습니다.");
//         System.out.println("noResult : " + noResult);
         noResult = "no";
         driver.navigate().back();
         driver.navigate().refresh();
      }catch(NoSuchElementException e) {
      
//         try {
			//article-title 게시글 제목
			 List<WebElement> title = driver.findElements(By.className("article-title"));
			 //article-content 게시글 내용
   //      List<WebElement> content =  driver.findElements(By.className("article-content"));
			 //article-region-name 판매 지역
			 List<WebElement> region = driver.findElements(By.className("article-region-name"));
			 //article-price 가격
			 List<WebElement> price = driver.findElements(By.className("article-price"));
			 //flea-market-article 게시글 전체 태그
			 List<WebElement> item = driver.findElements(By.className("flea-market-article"));
			 
//			 System.out.println("title size : " + title.size());
			 if(title.size() == 0) {
				 noResult = "no"; 
				 System.out.println("다시 검색해주세요.");
				 driver.navigate().back();
		         driver.navigate().refresh();
		         count = 0;
		         if(pf.onOff == "on") {
		        	 pf.onOff = "off";
		        	 driver.navigate().back();
			         driver.navigate().refresh();
		         }
				 return;}
			 
			 for (i = 0; i < title.size(); i++) {
//				 if(title.size() == 0) {break;}
//        	 if(driver.findElement(By.className("article-kind")) == null)
			    //게시글 제목 출력
			    System.out.println(i + 1 + ". " + title.get(i).getText());
			    //판매자 지역 출력
			    System.out.println(region.get(i).getText());
			    //가격 출력 만약 가격이 정해지지 않았다면 "-" 출력
			    if(price.get(i).getText().equals("-") || price.get(i).getText().equals("나눔")) {
			       System.out.println("- (판매자 문의)");
			    }else {
			       System.out.println(price.get(i).getText());
			    }
			    //라인 구분
			    System.out.println("============================");
			 }
			 
			 
			 int choicePd = 0;
			 //사용자가 최종으로 선택한 게시글 목록번호를 넣는 변수.
			 String num = "";
			 
			 
			 //더 보기
			 for (i = 0; i < 2; i++) {
//				 if(title.size() == 1) {break;}
//        	 System.out.println(choicePd + ". 1");
//        	 System.out.println(i);
			    System.out.print("게시글을 선택해주세요 (더 보기 = 0, 이전 = -1) : ");
			    choicePd = sc.nextInt();
			    
			    number2 = choicePd;
			    //이전을 선택할 시
			    if(choicePd == -1) {
			       driver.navigate().back();
			       driver.navigate().refresh();
			       search("");//2번 
			       choicePd = number2;
//               System.out.println("number2 : " + number2);
//               System.out.println(choicePd + ". 4");
			       break;
			       }
			    //더 보기를 선택할 시
			    if(choicePd == 0) {
			       //more-text 더 보기 클래스
			       WebElement more = driver.findElement(By.className("more-text"));
			       //더 보기를 클릭하여 추가 목록을 가져온다.
			       more.click();
			       //sleep을 걸어주어서 밑에 변수들에 새로운 정보가 담길 시간을 기다려준다.
			       try {Thread.sleep(2000);} catch (InterruptedException e2) {;}
			       title = driver.findElements(By.className("article-title"));
			       region = driver.findElements(By.className("article-region-name"));
			       price = driver.findElements(By.className("article-price"));
			       item = driver.findElements(By.className("flea-market-article"));
			       //num = item.get(choicePd - 1).findElement(By.tagName("a")).getAttribute("href");
			       for (int j = 0; j < title.size(); j++) {
			          System.out.println(j + 1 + ". " + title.get(j).getText());
			          System.out.println(region.get(j).getText());
			          if(price.get(j).getText().equals("-") || price.get(i).getText().equals("나눔")) {
			             System.out.println("- (판매자 문의)");
			          }else {
			             System.out.println(price.get(j).getText());
			          }
			          System.out.println("============================");
			       }
			    }else if(choicePd < 0 || title.size() < choicePd){
			       System.out.println("다시 선택해주세요.");
			       i--;
			    }else {
			       //게시글을 더 보지않고 바로 선택할 시
			       //num에는 상품목록번호가 들어간다.
			       num = item.get(choicePd - 1).findElement(By.tagName("a")).getAttribute("href");
			       //게시글의 제목을 클릭하면 게시글 본문으로 이동
			       title.get(choicePd - 1).click();
			       
			       try {Thread.sleep(3000);} catch (InterruptedException e1) {;}
			       System.out.println("✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭");
			       System.out.println("판매자 닉네임 : " + driver.findElement(By.id("nickname")).getText());
			       System.out.println("판매자 " + driver.findElement(By.id("article-profile-right")).getText());
			       System.out.println("");
			       System.out.println(driver.findElement(By.id("article-detail")).getText());
			       System.out.println("✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭");
			       String result = "";
				try {
					WebElement we2=  driver.findElement(By.id("article-price"));
					   String[] pdPrice2 = we2.getText().split(",");
					   for (int j = 0; j < pdPrice2.length; j++) {
						result += pdPrice2[j];
					   }
				} catch (NoSuchElementException e1) {
					System.out.println("쪽지를 남겨주세요.");
				}
//               result.replace("원", "");
					try {
						pdPrice = Integer.parseInt(result.replace("원", ""));
					} catch (NumberFormatException e1) {
						pdPrice = 0;
					}
			       //가격이 없을 때 생각...
			       
//               System.out.println("break");
			       break;
			    }
			 }
			 while(choicePd <= 0 || title.size() < choicePd) {
//				 if(title.size() == 0) {break;}
//        	 if(number == 10) {break;}
				 System.out.print("게시글을 선택해주세요 (더 보기 = 0, 이전 = -1) : ");
			    choicePd = sc.nextInt();
			    if(choicePd == -1) {
			    	driver.navigate().back();
			        driver.navigate().refresh();
//                System.out.println(i);
			        search("");
			        choicePd = number2;
//                System.out.println("number2 : " + number2);
//                System.out.println(choicePd + ". 4");
			        break;
			      }
			    if(choicePd < 0 || title.size() < choicePd){
			       System.out.println("다시 선택해주세요.");
			    }else if(choicePd != 0) {
			       num = item.get(choicePd - 1).findElement(By.tagName("a")).getAttribute("href");
//               System.out.println("num : " + num);
//               System.out.println("title : " + title);
//               System.out.println("count : " + count);
//               System.out.println("choicePd = " + choicePd);
//               System.out.println("number2 = " + number2);
//               System.out.println(title.get(choicePd - 1));
			       title.get(choicePd - 1).click();
			       try {Thread.sleep(3000);} catch (InterruptedException e1) {;}
			       System.out.println("✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭");
			       System.out.println("판매자 닉네임 : " + driver.findElement(By.id("nickname")).getText());
			       System.out.println("판매자 " + driver.findElement(By.id("article-profile-right")).getText());
			       System.out.println("");
			       System.out.println(driver.findElement(By.id("article-detail")).getText());
			       System.out.println("✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭✭");
			       break;
			       
//               num : https://www.daangn.com/articles/276306552
//            	   [[ChromeDriver: chrome on MAC (8b217dc560f5547b484e4c814b60a48f)] -> class name: article-title]
//            	   count : 0
//            	   choicePd = 1
//            	   number2 = 0
			       
//               break;
			    }else {
			       System.out.println("더 이상 없음");
			    }
			 }
//         System.out.println(num.split("/")[0]);
//         System.out.println(num.split("/")[1]);
//         System.out.println(num.split("/")[2]);
//         System.out.println(num.split("/")[3]);
			 if(count == 0) {
				 text_copy = num;
			 }
//         System.out.println("count : " + count);
//         System.out.println("text_copy : " + text_copy);
//         System.out.println("num : " + num);
//         System.out.println(num.split("/")[4]);
			 //이전을 누르고 search메소드 종료되고 나머지가 실행될 때 num에 값이 안 담김
			 pro_num = Integer.parseInt(text_copy.split("/")[4]);
//         System.out.println("pro_num : " + pro_num);
			 
//		} catch (ArrayIndexOutOfBoundsException e1) {
//			System.out.println("검색 결과가 없습니다.");
//			noResult = "no";
//			count = 0;
//			driver.navigate().back();
//	        driver.navigate().refresh();
//		} catch(Exception r) {
//			System.out.println(r);
//		}
      
      }//try-catch
      
//      WebElement hot = driver.findElement(By.linkText("인기매물 보기"));
//      hot.click();
//      try {Thread.sleep(3000);} catch (InterruptedException e) {;}
      
//      //중고거래 인기검색어
//      WebElement hot = driver.findElement(By.linkText("중고거래 인기검색어"));
//      hot.click();
//      try {Thread.sleep(3000);} catch (InterruptedException e) {;}
//      
//      //keyword-text 인기검색어 순위
//      for (WebElement el : driver.findElements(By.className("keyword-text"))) {
//         System.out.println(++rank + ". " + el.getText());
//      }
      count++;
   	}//method
      
      
   }//class