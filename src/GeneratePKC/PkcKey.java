package GeneratePKC;

public class PkcKey<T1, T2> {
	private T1 publicKey;
	private T2 privateKey;
	public T1 getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(T1 publicKey) {
		this.publicKey = publicKey;
	}
	public T2 getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(T2 privateKey) {
		this.privateKey = privateKey;
	}
	
	
}
