package helpers;

public class Test {

	public static void main(String[] args) {
		LiveEncryption liveEncryption = new LiveEncryption();
		liveEncryption.generateKey();
		System.out.println("private " + liveEncryption.getPrivateKeyAsString());
		System.out.println("public " + liveEncryption.getPublicKeyAsString());
		String encrypted1 = liveEncryption.encryptAndReturnString("hello there");
		System.out.println(encrypted1);
		String encrypted2 = liveEncryption.encryptAndReturnString("hello there");
		System.out.println(encrypted2);
		String encrypted3 = liveEncryption.encryptAndReturnString("hello there");
		System.out.println(encrypted3);
		String encrypted4 = liveEncryption.encryptAndReturnString("hello there");
		System.out.println(encrypted4);
		
		String decrypted1 = liveEncryption.decryptAndReturnString(encrypted1);
		System.out.println(decrypted1);
		String decrypted2 = liveEncryption.decryptAndReturnString(encrypted2);
		System.out.println(decrypted2);
		String decrypted3 = liveEncryption.decryptAndReturnString(encrypted3);
		System.out.println(decrypted3);
		String decrypted4 = liveEncryption.decryptAndReturnString(encrypted4);
		System.out.println(decrypted4);
		

	}

}
