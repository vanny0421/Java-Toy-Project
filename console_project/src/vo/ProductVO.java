package vo;

public class ProductVO {
	/*관심 목록 테이블*/
	private int b_no;			//관심 목록 번호
	private int b_u_no;		//회원 번호
	private int b_p_no;		//상품 번호

	public ProductVO() {;}

	public int getB_no() {
		return b_no;
	}

	public void setB_no(int b_no) {
		this.b_no = b_no;
	}

	public int getB_u_no() {
		return b_u_no;
	}

	public void setB_u_no(int b_u_no) {
		this.b_u_no = b_u_no;
	}

	public int getB_p_no() {
		return b_p_no;
	}

	public void setB_p_no(int b_p_no) {
		this.b_p_no = b_p_no;
	}
	
	
}
