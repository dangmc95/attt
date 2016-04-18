package GeneratePKC;

import java.util.Random;

public class MerkleHellman {
	
	public static final int base = 100000;
	public static final int N = 64;
	private static int[] a;
	private static int[] aSuperInc;
	private static int m, w;
	
	public static void init() {
		a = new int[N];
		aSuperInc = new int [N];
		m = w = 0;
		aSuperInc[0] = 1;
	}
	
	public static int gcd(int a, int b) {
		if (b == 0) return a;
		return gcd(b, a % b);
	}
	
	public static int gcdOpen(int n1, int n2) {
		int a1 = 1, b1 = 0;
		int a2 = 0, b2 = 1;
		while (n1 % n2 != 0) {
			int p = n1 / n2;;
			int r = n1 % n2;
			n1 = n2;
			n2 = r;
			int tmp = a2;
			a2 = a1 - p * a2;
			a1 = tmp;
			tmp = b2;
			b2 = b1 - p*b2;
			b1 = tmp;
		}
		return a2;
	}
	
	public static PkcKey GeneratePKC() {
		init();
		/* Generate aSuperInc is a vector super Increasing */
		int[] sum = new int[N];
		sum[0] = aSuperInc[0];
		Random rand = new Random();
		for (int i = 1; i < N; i++) {
			int x = rand.nextInt() % base;
			aSuperInc[i] = sum[i-1] + x;
			sum[i] = sum[i-1] + aSuperInc[i];
		}
	
		/* Random m, w such that m >= P(sum of vector aSuperInc) and gcd(m, w) = 1 */
		int number = rand.nextInt() % base;
		m = sum[N-1] + number;
		while (true) {
			w = rand.nextInt() % base;
			if (gcd(m, w) == 1) break;
		}
		
		/* Create PKC Key*/
		PkcKey pkcKey = new PkcKey<>();
		
		/* Create Public Key */
		for (int i = 0; i < N; i++) 
			a[i] = ((aSuperInc[i] % m) * w) % m;
		PublicKey publicKey = new PublicKey();
		publicKey.setA(a);
		pkcKey.setPublicKey(publicKey);
		
		/* Create Private Key */
		PrivateKey privateKey = new PrivateKey();
		privateKey.setaSuperInc(aSuperInc);
		privateKey.setM(m);
		privateKey.setW(w);
		pkcKey.setPrivateKey(privateKey);
		
		return pkcKey;
	}
	
	public static int encode(int[] plainText, PublicKey publicKey) {
		int cipherText = 0;
		a = publicKey.getA();
		for (int i = 0; i < plainText.length; i++)
			cipherText += plainText[i] * a[i];
		return cipherText;
	}
	
	public static int[] decode(int cipherText, PrivateKey privateKey){
		int[] plainText = new int[N];
		int wReverser = gcdOpen(privateKey.getW(), privateKey.getM());
		cipherText = (cipherText * wReverser) % privateKey.getM();
		aSuperInc = privateKey.getaSuperInc();
		for (int i = aSuperInc.length - 1; i >= 0; i--) {
			plainText[i] = 0;
			if (cipherText > aSuperInc[i]) {
				plainText[i] = 1;
				cipherText -= aSuperInc[i];
			}
		}
		return plainText;
	}
	public static void main(String[] agrs){
		PkcKey pkcKey = MerkleHellman.GeneratePKC();
		PublicKey publicKey = (PublicKey) pkcKey.getPublicKey();
		PrivateKey privateKey = (PrivateKey) pkcKey.getPrivateKey();
		System.out.print("Public Key: ");
		for (int i = 0; i < publicKey.getA().length; i++)
			System.out.print(publicKey.getA()[i] + " ");
		System.out.print("|||||||||||||||||||||||||||||||||||||||||");
		System.out.print("Private Key");
		System.out.print("M = " + privateKey.getM());
		System.out.print("W = " + privateKey.getW());
		System.out.print("Vector Super Increasing = ");
		for (int i = 0; i < privateKey.getaSuperInc().length; i++) 
			System.out.print(privateKey.getaSuperInc()[i] + " ");
	}
}
	
