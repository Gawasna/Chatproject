package SampleCode;
import java.util.Random;
	public class Randomlogin {
	    public static void main(String[] args) {
	        System.out.println(generateUsername(8));
	    }

	    public static String generateUsername(int length) {
	        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	        StringBuilder sb = new StringBuilder();
	        Random random = new Random();
	        for (int i = 0; i < length; i++) {
	            int index = random.nextInt(alphabet.length());
	            char randomChar = alphabet.charAt(index);
	            sb.append(randomChar);
	        }

	        String randomString = sb.toString();
	        return "guest_" + randomString;
	    }
	}


