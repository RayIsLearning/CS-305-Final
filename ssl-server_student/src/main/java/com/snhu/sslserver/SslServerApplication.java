package com.snhu.sslserver;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}

@RestController
class ServerController{
//Adds hash function to return the checksum value for the data string that contains my name.    
	public static String calcHash(String name) throws NoSuchAlgorithmException{//method generates SHA-256 hash
		MessageDigest md = MessageDigest.getInstance("SHA-256");//creates method digest object and initialize with algorithm cipher
		byte[] hash = md.digest(name.getBytes(StandardCharsets.UTF_8));//defines hash type
		BigInteger number = new BigInteger(1, hash);
		StringBuilder hexString = new StringBuilder(number.toString(16));//converts to hex
	
		while (hexString.length() < 32)	{
			hexString.insert(0, '0');
		}
		
		return hexString.toString();
		
	}
	@RequestMapping("/hash")
	public String myHash() {//prints welcome message and returns data string with hash 
	    String data = "Hello Ray Cooke!";
	    
	    try {
	        String hash = calcHash(data);
	        return "<p>data: " + data + "</p><p>hash: " + hash + "</p>";
	    } 
	    
	    catch (NoSuchAlgorithmException e) {
	        return "Error calculating hash: " + e.getMessage();
	    }
	}
}