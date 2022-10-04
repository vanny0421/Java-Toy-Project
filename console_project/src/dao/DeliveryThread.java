package dao;

public class DeliveryThread implements Runnable {

	@Override
	public void run() {
		int Preparing = 3;// 상품준비 시간
		int deliveryTime = 5;// 배송시간

		System.out.println("상품 준비중");
		for (int i = 0; i < Preparing; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				;
			}
			System.out.println("⏳");
		}
		System.out.println("-------------");
		System.out.println("상품 준비 완료");

		System.out.println("배송중");
		for (int i = 0; i < deliveryTime; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				;
			}
			System.out.println("🎁");
		}
		System.out.println("-------------");
		System.out.println("배송완료╰(*°▽°*)╯");
	}

}