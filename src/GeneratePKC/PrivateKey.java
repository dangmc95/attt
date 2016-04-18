package GeneratePKC;

public class PrivateKey {
	public static final int N = 64;
	private int[] aSuperInc;
	private int m, w;
	
	public PrivateKey() {
		aSuperInc = new int[N];
		m = 0;
		w = 0;
	}

	public int[] getaSuperInc() {
		return aSuperInc;
	}

	public void setaSuperInc(int[] aSuperInc) {
		this.aSuperInc = aSuperInc;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}
	
	
}
