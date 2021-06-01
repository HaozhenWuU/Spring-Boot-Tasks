package com.example.springgumball;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.net.InetAddress;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Encoder;
import java.sql.Timestamp;  
import java.lang.Math; 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

import com.example.gumballmachine.GumballMachine ;


@Slf4j
@Controller
@RequestMapping("/")
public class GumballMachineController {


	private String hmac_sha256(String secretKey, String data) {
		try {
			Mac mac = Mac.getInstance("HmacSHA256") ;
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256") ;
			mac.init(secretKeySpec) ;
			byte[] digest = mac.doFinal(data.getBytes()) ;
			java.util.Base64.Encoder encoder = java.util.Base64.getEncoder() ;
			String hash = encoder.encodeToString(digest);
			return hash;
		} catch (InvalidKeyException e1) {
			throw new RuntimeException("Invalid key exception while converting to HMAC SHA256") ;
		} catch (NoSuchAlgorithmException e2) {
			throw new RuntimeException("Java Exception Initializing HMAC Crypto Algorithm") ;
		}
	}

	private String SerialNum(){

		Random random = new Random();
		Integer num = Math.abs(random.nextInt());

		String serial = Integer.toString(num);

		return serial;
	}

	private String ModelNum(){

		Random random = new Random();
		Integer num = Math.abs(random.nextInt(10000));

		String model = Integer.toString(num);

		return model;

	}


	private static String key = "kwRg54x2Go9iEdl49jFENRM12Mp711QI" ;
	private String Model_num = ModelNum();
	private String Serial_num = SerialNum();
	private Integer total_count = 100; 

	private GumballModel gmd;

	@Autowired  
	private DataRepository dr;
	
	
	@GetMapping
	public String getAction( @ModelAttribute("command") GumballCommand command, 
		Model model) {
		


		log.info( "Model: " + Model_num ) ;
		log.info( "Serial: " + Serial_num ) ;


        // add model number and seruial number to the databasse
		gmd = new GumballModel();
		gmd.setModelNumber(Model_num);
		gmd.setSerialNumber(Serial_num);
		
		
		GumballMachine gm = new GumballMachine() ;
		gm.setSerialNumber(gmd.getSerialNumber());
		gm.setModelNumber(gmd.getModelNumber());
		String message = gm.toString() ;

		String state = gm.getState().getClass().getName();

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String ts = timestamp.toString();

        //generate hash 
		String hash_text = state + "" + ts;
		String hash = hmac_sha256( key, hash_text);
		
		command.setState(state) ;
		command.setTimestamp(ts);
		command.setHash(hash);

		String server_ip = "" ;
		String host_name = "" ;
		try { 
			InetAddress ip = InetAddress.getLocalHost() ;
			server_ip = ip.getHostAddress() ;
			host_name = ip.getHostName() ;
			
		} catch (Exception e) { }
		
		model.addAttribute( "hash", hash ) ;
		model.addAttribute( "message", message ) ;
		model.addAttribute( "server",  host_name + "/" + server_ip ) ;

		return "gumball" ;

	}

	@PostMapping
	public String postAction(@Valid @ModelAttribute("command") GumballCommand command,  
		@RequestParam(value="action", required=true) String action,
		Errors errors, Model model, HttpServletRequest request) {
		
		log.info( "Action: " + action ) ;
		log.info( "Command: " + command ) ;

		
		


		String oldHash = command.getHash();
		String oldState = command.getState();
		String oldStamp = command.getTimestamp();
		String hash_text = oldState + "" + oldStamp;

		String newHash = hmac_sha256( key, hash_text );


		GumballMachine gm = new GumballMachine();

		gmd = new GumballModel();
		

		log.info( "Model: " + Model_num ) ;
		log.info( "Serial: " + Serial_num ) ;

		
		
		gm.setState(oldState);

		
		if ( action.equals("Insert Quarter") ) {
			gm.insertQuarter() ;
		}

		if ( action.equals("Turn Crank") ) {
			total_count -= 1;
			gmd.setCountGumballs(total_count);
			
			gmd.setSerialNumber(Serial_num);
			gmd.setModelNumber(Model_num);
			dr.save(gmd);
			command.setMessage("") ;
			gm.turnCrank() ;
		} 

		
		
		String message = gm.toString() ;
		String server_ip = "" ;
		String host_name = "" ;
		try { 
			InetAddress ip = InetAddress.getLocalHost() ;
			server_ip = ip.getHostAddress() ;
			host_name = ip.getHostName() ;
			
		} catch (Exception e) { }
		
		model.addAttribute( "hash", newHash ) ;
		model.addAttribute( "message", message ) ;
		model.addAttribute( "server",  host_name + "/" + server_ip ) ;
		

		if (errors.hasErrors()) {
			return "gumball";
		}

		return "gumball";
	}

}