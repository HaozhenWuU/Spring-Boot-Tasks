
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Encoder;
import java.sql.Timestamp;  

class HMACTester {

	private static String key = "kwRg54x2Go9iEdl49jFENRM12Mp711QI" ;

 
	public static void main(String[] args) { 

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		String ts = timestamp.toString();

		byte[] hash1 = hmac_sha256( key, ts ) ;
		

		java.util.Base64.Encoder encoder = java.util.Base64.getEncoder() ;

		System.out.println ( "MSG1: " + ts ) ;
		System.out.println ( "HASH2: " + encoder.encodeToString(hash1) )  ;

	}

	private static byte[] hmac_sha256(String secretKey, String data) {
		try {
			Mac mac = Mac.getInstance("HmacSHA256") ;
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256") ;
			mac.init(secretKeySpec) ;
			byte[] digest = mac.doFinal(data.getBytes()) ;
			return digest ;
		} catch (InvalidKeyException e1) {
			throw new RuntimeException("Invalid key exception while converting to HMAC SHA256") ;
		} catch (NoSuchAlgorithmException e2) {
			throw new RuntimeException("Java Exception Initializing HMAC Crypto Algorithm") ;
		}
	}

}

