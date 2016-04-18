package GeneratePKC;

public class PublicKey {
	public static final int N = 64;
	private int[] a;
	
	public PublicKey() {
		a = new int[N];
	}
	
	public void setA(int[] a) {
		this.a = a;
	}
	
	public int[] getA() {
		return a;
	}
	
}
