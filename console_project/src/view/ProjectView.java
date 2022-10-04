package projectView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.openqa.selenium.By;

import dao.ChatDAO;
import dao.Crawling;
import dao.Crawling2;
import dao.MoneyDAO;
import dao.ProductDAO;
import dao.PurchasesDAO;
import dao.TradeDAO;
import dao.UserDAO;
import vo.TradeVO;
import vo.UserVO;

public class projectForm {
	public static String onOff;
	
	public static void main(String[] args) {

		UserDAO dao = new UserDAO();
		UserVO vo = new UserVO();
		Scanner sc = new Scanner(System.in);
		Crawling cw = new Crawling();
		ProductDAO p_dao = new ProductDAO();
		ChatDAO c_dao = new ChatDAO();
		MoneyDAO m_dao = new MoneyDAO();
		DecimalFormat decimal = new DecimalFormat("###,###");
		PurchasesDAO p2_dao = new PurchasesDAO();

		String title = "HI WELCOME TO SALGU YA MARKET \n 어서오세요 〰❕ ❕ 🍑 중 고 거 래 🍑살 구 야 입니다 〰❕ ❕ \n\n 👇 👇 아래에서 메뉴 선택 해주세요\n";
		String mainMenu = "1.   로그인\n" + "2.   회원가입\n" + "3.   상품검색\n" + "4.   나가기\n";
		String myMenu = "1.   내 프로필 보기\n" + "2.   쪽지함\n" + "3.   상품검색\n" + "4.   관심목록\n" + "5.   구매내역\n" + "6.   내 계좌\n"
				+ "7.   회원탈퇴\n" + "8.   로그아웃\n";
		String profile = "1.   내 정보 수정\n" + "2.   뒤로가기\n";
		String restoreMsg = "계정을 복구하시겠습니까?\n1.\t네\n2.\t아니오";

		String choiceMsg = "실행할 번호를 입력해주세요 : ";
		String line = "======================";
		String ln = "\n";

		int restore = 0;
		int menuChoice = 0;
		String phoneNumber = "";
		boolean main = true;

		System.out.println();
		System.out.println(title);
		// mainMenu를 while문에 넣어서 탈출할 때 까지 메뉴를 띄운다(?)
		while (true) {
			System.out.println(mainMenu);
			// mainMenu를 선택할 변수
			System.out.print(choiceMsg);
			menuChoice = sc.nextInt();
			System.out.println();
			// 만약 나가기를 택하면 while문 탈출하여 메인메소드 종료
			if (menuChoice == 4) {
				break;
			}

			switch (menuChoice) {
			case 1: // 로그인
				System.out.println("[로 그 인]");
				System.out.println(line);
				System.out.println();
				System.out.print("휴대폰 번호를 입력해주세요 : ");
				// 사용자가 입력한 폰 번호
				vo.setU_phoneNum(phoneNumber = sc.next());
				if (phoneNumber.length() != 11) {
					System.out.println("11자리만 입력해주세요(- 제외)");
					break;
				}
				System.out.println();

				// 위에서 사용자가 입력한 폰 번호를 DB에 조회해서 겹치는 폰 번호가 있다면
				// 그 회원의 정보를 가져온다.
				if (dao.checkPhoneNum(vo.getU_phoneNum())) {
					vo = dao.viewProfile();
					if (vo.getU_nonRegi() == 1) {
						System.out.println("탈퇴된 회원입니다.");
						System.out.println(restoreMsg);
						System.out.println(choiceMsg);
						restore = sc.nextInt();
						boolean check = true;
						while(check) {
							switch(restore) {
							case 1:
								System.out.print("핸드폰 번호를 입력해주세요 : ");
								if(dao.restore(sc.next())) {
									System.out.println("복구 성공");
								}else {
									System.out.println("복구 실패");
								}
								check = false;
								break;
							case 2:
								check = false;
								break;
							}
						}
						break;
					}
					System.out.println("로그인에 성공했습니다.");
					System.out.println();

					// 로그인에 성공하면 로그인을 했을 때 사용가능한 메뉴를 while문에 넣어
					// 탈출할 때 까지 메뉴를 반복시켜준다.
					while (main) {
						System.out.println(myMenu);
						System.out.print(choiceMsg);
						menuChoice = sc.nextInt();
						// 로그아웃을 택하면 회원 고유번호인 session이 0이되어서 while문 탈출
						if (menuChoice == 8) {
							dao.logout();
							break;
						}

						// 내 정보가 정보 보기를 택했을 때만 나오기 위해서 만든 플래그
						boolean profileCheck = true;
						switch (menuChoice) {

						case 1: // 내 정보 보기

							// 내 정보를 뿌려주고 선택
							while (profileCheck) {

								// 내 프로필 보는 메소드
								vo = dao.viewProfile();
								System.out.println("회원번호 :\t" + vo.getU_no());
								System.out.println("이름 :\t\t" + vo.getU_nickName());
								System.out.println("이메일 :\t" + vo.getU_email());
								System.out.println("전화번호 :\t" + vo.getU_phoneNum());
								System.out.println("생년월일 :\t" + vo.getU_birthDate());
								System.out.println("거주지역 :\t" + vo.getU_city());
								System.out.println("은행명 :\t" + vo.getU_bankName());
								System.out.println("계좌번호 :\t" + vo.getU_accountNum());
								System.out.println("잔액 : \t" + decimal.format(vo.getU_totalPrice()) + " 원");
								System.out.println();
								// 뒤로가기
								if (menuChoice == 2) {
									break;
								}

								System.out.println(profile);
								System.out.println(choiceMsg);
								menuChoice = sc.nextInt();
								switch (menuChoice) {

								case 1: // 내 정보 수정
									// 정보 수정에서 이용하는 플래그
									boolean flag = true;
									vo = dao.viewProfile();
									while (flag) {
										System.out.println(
												"1. 닉네임 변경\n 2. 이메일 수정\n 3. 휴대폰 번호 수정\n 4. 거주지 변경\n 5. 계좌번호 변경\n 6. 수정하기\n 7.나가기");
										int choice = sc.nextInt();

										switch (choice) {
										// 사용자가 입력한 값으로 수정
										case 1:
											System.out.print("닉네임 변경 :   ");
											vo.setU_nickName(sc.next());
											break;
										case 2:
											System.out.print("이메일 변경 :   ");
											vo.setU_email(sc.next());
											break;
										case 3:
											System.out.print("휴대폰 번호 변경 :   ");
											vo.setU_phoneNum(sc.next());
											break;
										case 4:
											System.out.print("예) 거주지역 :   ");
											System.out.println("사랑시 고백구");
											System.out.print("거주지역 :   ");
											vo.setU_city(sc.next());
											break;
										case 5:
											System.out.print("계좌번호 변경 :   ");
											vo.setU_accountNum(sc.next());
											break;
										case 6:
											dao.changeInfo(vo);
											System.out.println("수정이 완료 되었습니다.   ");
											break;
										case 7:
											// 나가기를 누르면 while문 탈출
											flag = false;
											profileCheck = false;
											break;
										}
									}
									break;

								case 2:
									profileCheck = false;
									break;
								// 뒤로가기
								default:
									profileCheck = false;
									System.out.println("다시 선택해주세요.");
									// 여기 다시 선택해달라는 출력창 띄우면 좋을 거 같음
									break;
								}

							}

							break;

						case 2: // 쪽지확인
							// 사용자가 입력한 폰 번호를 DB에 조회해서 있으면
							// 그 사용자의 쪽지를 가져온다.
							ArrayList<String> list = c_dao.viewChat(phoneNumber);
							// 사용자의 닉네임, 상품번호, 내용을 "/"로 나누어서 저장을 했기 때문에
							// split("/)을 사용해서 보기 편하게 나타냈다.
							String pro_message[];
							for (int i = 0; i < list.size(); i++) {
								pro_message = list.get(i).split("/");
								System.out.println((i + 1) + ". " + "보낸 회원 닉네임 : " + pro_message[2]);
								System.out.println("상품 번호 : " + pro_message[0]);
								System.out.println("내 용 : " + pro_message[1]);
								System.out.println();
							}

							if (list.size() != 0) {
								boolean flag = true;
								while (flag) {
									String message = "1. 상품정보 입력(상품 보기)\n2. 나가기";
									System.out.println(message);
									System.out.println(choiceMsg);
									int response = sc.nextInt();

									if (response == 1) {

										// 크롤링
										System.out.print("상품의 번호 입력 : ");
										int pp_no = sc.nextInt();
										
										try {
											Crawling2 cw2 = new Crawling2(pp_no);
											cw2.search();
										} catch (InputMismatchException e) {
											System.out.println("게시글이 내려갔거나 팔린 상품입니다.");
										}

										
									} else if (response == 2) {
										flag = false;
									} else {
										System.out.println("다시 선택해주세요.");
									}
								}
							} else {
								System.out.println("쪽지가 없습니다.");
							}

							break;

						case 3: // 상품 검색
							boolean check = true;
//							try {
								cw.search("");
//							} catch (ArrayIndexOutOfBoundsException e1) {
//								System.out.println("다시 검색해 주세요.");
//								check = false;
//							}

							while (check) {
								if(cw.noResult.equals("no")) {cw.count = 0; break;}
								System.out.println("무엇을 하시겠어요?");
								System.out.println("1.   찜하기 ❤");
								System.out.println("2.   쪽지하기");
								System.out.println("3.   구매하기");
								System.out.println("4.   이전 메뉴로 가기");
								System.out.println("5.   메인 메뉴로 가기");
								System.out.print(choiceMsg);
								int choice = sc.nextInt();

								switch (choice) {
								case 1:// 찜하기
									if (UserDAO.session != 0) {
										// 판매목록에 저장
										p_dao.insertProductNum(Crawling.pro_num);
										// 찜목록에 저장
										p_dao.insertBookmark(UserDAO.session, Crawling.pro_num);
										System.out.println("관심목록에 추가되었습니다 🖤");
									} else {
										System.out.println("로그인 상태에서 사용가능한 메뉴입니다.");
										check = false;
										break;
									}
									check = false;
									break;
								case 2:// 쪽지하기
									if (UserDAO.session != 0) {

										if (!(UserDAO.session == 23)) { // 관리자로 로그인을 안했으면
											System.out.println("쪽지 보낼 내용을 입력해 주세요.");
											sc.nextLine();
											String message = sc.nextLine();
											// 관리자의 쪽지함에 내 회원번호와 쪽지와 상품번호를 전송
											c_dao.sendChat(UserDAO.session, "01011112222", message, Crawling.pro_num);

											System.out.println("쪽지가 성공적으로 전송되었습니다.");
										} else {
											System.out.println("본인에게 쪽지 전송이 불가합니다😌");
										}

									}

									check = false;
									break;
								case 3:// 구매하기
									TradeVO tvo = new TradeVO();
									TradeDAO tdao = new TradeDAO();

									// 크롤링해서 검색한 상품의 가격과, 23(관리자의 회원번호)를 입력받는다.
									if (m_dao.sendMoney(Crawling.pdPrice, 23)) {
//										if(Crawling.pdPrice == 0) {
//											System.out.println("쪽지로 문의해주세요.");
//											break;
//										}else {
										System.out.println("송금 성공");
										vo = dao.viewProfile();
										System.out.println("잔액 : " + decimal.format(vo.getU_totalPrice()) + " 원");
										// 판매 게시글의 제목과 닉네임 구매한 유저의 닉네임을 DB에 저장
										tvo.setP_name(Crawling.driver.findElement(By.id("article-title")).getText());
										tvo.setSell_user_nickname(
												Crawling.driver.findElement(By.id("nickname")).getText());
										tvo.setBuy_user_nickname(dao.getNickName());
										tdao.purchase(tvo);
										check = false;
//										}

										// 만약 구매하려는 상품이 가진 잔액보다 비싸거나
										// - (판매자 문의)일 경우 밑에 문구를 출력
									} else {
										System.out.println("송금 실패");
										System.out.println("쪽지로 문의해주세요.");
										vo = dao.viewProfile();
										System.out.println("잔액 : " + decimal.format(vo.getU_totalPrice()) + " 원");
										check = true;
									}
									break;
								case 4:// 이전
									cw.search(Crawling.searchText);
									onOff = "on";
									check = true;
									break;
								case 5:
									check = false;
									break;
								default:
									System.out.println("다시 선택해주세요.");
								}
							}
							break;

						case 4:
							System.out.println("내 찜목록");
							// 로그인한 회원의 찜목록을 출력
							ArrayList<Integer> pdNum = p_dao.bookmarkP_number(UserDAO.session);
							if(pdNum.size() == 0) {System.out.println("찜 목록이 존재하지 않습니다.");break;}
							for (int i = 0; i < pdNum.size(); i++) {
								System.out.println(i + 1 + ". " + pdNum.get(i));

							}

							System.out.print("보고 싶은 찜 목록을 선택해 주세요 : ");
							int choice = sc.nextInt();

							// 찜목록을 선택하면 그 상품의 게시글 내용을 출력
							try {
								Crawling2 cw2 = new Crawling2(pdNum.get(choice - 1));
								cw2.search();
							} catch (InputMismatchException e) {
								System.out.println("게시글이 내려갔거나 팔린 상품입니다.");
							}

							System.out.println("1.   구매하기\n2.   찜 삭제\n3.   나가기");
							System.out.print(choiceMsg);
							int selectNum = sc.nextInt();
							boolean flag = true;
							while (flag)
								switch (selectNum) {
								case 1:
									TradeVO tvo = new TradeVO();
									TradeDAO tdao = new TradeDAO();
									// 고쳐야 할 것
									if (m_dao.sendMoney(Crawling2.pdPrice3, 23)) {
										System.out.println("송금 성공");
										vo = dao.viewProfile();
										System.out.println("잔액 : " + decimal.format(vo.getU_totalPrice()) + " 원");
										tvo.setP_name(Crawling2.driver.findElement(By.id("article-title")).getText());
										tvo.setSell_user_nickname(
												Crawling2.driver.findElement(By.id("nickname")).getText());
										tvo.setBuy_user_nickname(dao.getNickName());
										tdao.purchase(tvo);
									} else {
										System.out.println("[송금 실패] 잔액이 부족합니다. 잔액충전을 해주세요.");
										vo = dao.viewProfile();
										System.out.println("잔액 : " + decimal.format(vo.getU_totalPrice()) + " 원");
									}
									flag = false;
									break;
								case 2:
									// 사용자가 입력한 번호의 품목 삭제
									if (p_dao.dropBookmark(UserDAO.session, pdNum.get(choice - 1))) {
										System.out.println("찜 삭제");
									} else {
										System.out.println("삭제 안됨");
									}
									flag = false;
									break;
								case 3:
									flag = false;
									break;
								default:
									System.out.println("다시 선택해 주세요");
									break;
								}
							break;

						case 5: // 구매내역
							ArrayList<String> list2 = p2_dao.purchaseDetails(dao.session);
							String pro_message2[];
							for (int i = 0; i < list2.size(); i++) {
								pro_message2 = list2.get(i).split("/");
								System.out.println((i + 1) + "번 구매목록");
//		                        System.out.println(pro_message2[0]);
								System.out.println("상품 내용 :\t" + pro_message2[1]);
								System.out.println("결제 수단 :\t" + pro_message2[2]);
								System.out.println("결제일 :\t" + pro_message2[3]);
//		                        System.out.println(""+pro_message2[4]);
								System.out.println();
							}
							break;
						case 6: // 내 계좌 보기
							vo = dao.viewProfile();
							System.out.println("은행 : " + vo.getU_bankName());
							System.out.println("계좌 번호 : " + vo.getU_accountNum());
							System.out.println("잔액 : " + decimal.format(vo.getU_totalPrice()) + " 원");

							boolean moneyCheck = true;
							while (moneyCheck) {
								System.out.println("1. 입급\n2. 출금\n3. 나가기");
								System.out.println(choiceMsg);
								int moneySelect = sc.nextInt();
								int deposit = 0;

								switch (moneySelect) {
								case 1:
									System.out.print("얼마를 입금 하시겠습니까? : ");
									deposit = sc.nextInt();
									if (m_dao.deposit(deposit)) {
										System.out.println("입금 완료");
									} else {
										System.out.println("입금 실패");
									}

//                                 DecimalFormat decFormat = new DecimalFormat("###,###");
									vo = dao.viewProfile();
									System.out.println("잔액 : " + decimal.format(vo.getU_totalPrice()) + " 원");
									break;
								case 2:
									System.out.print("얼마를 출금 하시겠습니까? : ");
									deposit = sc.nextInt();
									if (m_dao.withdraw(deposit)) {
										System.out.println("출금 완료");
									} else {
										System.out.println("잔액이 부족합니다.");
									}

									vo = dao.viewProfile();
									System.out.println("잔액 : " + decimal.format(vo.getU_totalPrice()) + " 원");
									break;
								case 3:
									moneyCheck = false;
									break;
								default:
									System.out.println("다시 선택해주세요.");
									break;
								}
							}

							break;

						case 7: // 회원탈퇴

							if (UserDAO.session != 0) {
								System.out.print("회원탈퇴\n핸드폰 번호 : ");
								if (dao.drop(sc.next())) {
									System.out.println("회원탈퇴 성공");
									System.out.println("다음에 또 찾아주세요~~");
								} else {
									System.out.println("회원탈퇴 실패");
								}
							} else {
								System.out.println("로그인 후 이용 가능한 서비스입니다.");
							}
							main = false;
							break;
						case 8:// 로그아웃
							dao.logout();
							break;
						}
					}

				} else {
					System.out.println("회원이 아니에요..😂 회원가입 해주세요.");
				}
				// dao.sendNumber(phoneNumber=sc.next());
//            
//            System.out.print("인증번호 : ");
//            number = sc.next();
//             if(!userDAO.number.equals(number)) {
//                System.out.println("인증실패");
//             }else {
//                System.out.println("로그인 성공");
//             }
				break;
			case 2: // 회원가입
				// 비로그인

				System.out.println("[ 회 원 가 입 ]");
				System.out.println();
				System.out.println("↓ 휴대폰 번호 중복 검사");
				System.out.println();
				System.out.print("휴대폰 번호 :\t");
				System.out.println();
				phoneNumber = sc.next();
				if (phoneNumber.length() != 11) {
					System.out.println("11자리만 입력해주세요(- 제외)");
					break;
				}
				if (!dao.checkPhoneNum(phoneNumber)) {
					vo.setU_phoneNum(phoneNumber);

					System.out.println(line);
					System.out.print("이름 :\t");
					vo.setU_nickName(sc.next());

					System.out.print("메일 :\t");
					vo.setU_email(sc.next());

					System.out.print("생년월일 :\t");
					vo.setU_birthDate(sc.next());

					System.out.println("예) 거주지역 :\t 사랑시 고백구");
					System.out.print("거주지역 :\t");
					sc.nextLine();
					vo.setU_city(sc.nextLine());

					System.out.print("은행명 :\t");
					vo.setU_bankName(sc.next());

					System.out.print("계좌번호 :\t");
					vo.setU_accountNum(sc.next());

					dao.join(vo);

					System.out.println("회원가입을 성공했습니다. 로그인 해주세요.");
					System.out.println();

				}else {
//          dao.sendNumber(phoneNumber);
//          
//          System.out.print("인증번호 : ");
//          number = sc.next();
//           if(!UserDAO.number.equals(number)) {
//              System.out.println("인증실패");
//           }else {
//              dao.join(vo);
//              System.out.println("회원가입 성공");
//           }
				System.out.println("이미 가입 된 번호입니다.");
				}
				break;
				
			case 3: // 상품 검색
				// 비로그인
				boolean check = true;
//				try {
					cw.search("");
//				} catch (ArrayIndexOutOfBoundsException e1) {
//					System.out.println("다시 검색해 주세요.");
//					check = false;
//				}

				while (check) {
					if(cw.noResult.equals("no")) {cw.count = 0; break;}
					System.out.println("무엇을 하시겠어요?");
					System.out.println("1. 찜하기");
					System.out.println("2. 쪽지하기");
					System.out.println("3. 구매하기");
					System.out.println("4. 메인화면으로");
					System.out.print("선택 : ");
					int choice = sc.nextInt();
					switch (choice) {
					case 1:
						System.out.println("로그인이 필요한 서비스입니다.");
						break;
					case 2:
						System.out.println("로그인이 필요한 서비스입니다.");
						break;
					case 3:
						System.out.println("로그인이 필요한 서비스입니다.");
						break;
					case 4:
//                  cw.search(Crawling.searchText);
						check = false;
						break;
					default:
						System.out.println("다시 선택해주세요.");
					}
				}

				break;
			default:
				System.out.println("다시 선택해주세요.");
				break;
			}// switch 메인메뉴 선택
		} // while
	} // main
} // class