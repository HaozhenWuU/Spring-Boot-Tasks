package com.example.springpayments;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Optional;
import java.time.*; 
import java.util.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import com.example.springcybersource.*;

@Slf4j
@Controller
@RequestMapping("/")
public class PaymentsController {  


  @Autowired  
  private PaymentRepo repo;

  private static boolean DEBUG = true ;

  @Value("${cybersource.apihost}") String apiHost ;
  @Value("${cybersource.merchantkeyid}")  String merchantKeyId ;
  @Value("${cybersource.merchantsecretkey}")  String merchantsecretKey ;
  @Value("${cybersource.merchantid}")  String merchantId ;


  @Data
  class Message{
    private String msg;
    public Message(String m){
      msg = m;}
    }


    class ErrorMessages{
      private ArrayList<Message> messages = new ArrayList<Message>();
      public void add(String msg){
        messages.add(new Message(msg));
      }    
      public ArrayList<Message> getMessages(){
        return messages;
      }
      public void print(){
        for(Message m:messages){
          System.out.print(m.msg);
        }
      }

    }



    private static Map<String,String> months = new HashMap<>();
    static{
      months.put("January","01");
      months.put("February","02");
      months.put("March","03");
      months.put("April","04");
      months.put("May","05");
      months.put("June","06");
      months.put("July","07");
      months.put("August","08");
      months.put("September","09");
      months.put("October","10");
      months.put("November","11");
      months.put("December","12");
    }

    private static Map<String,String> states = new HashMap<>();
    static{
      states.put("AL", "Alabama");
      states.put("AK", "Alaska");
      states.put("AB", "Alberta");
      states.put("AZ", "Arizona");
      states.put("AR", "Arkansas");
      states.put("BC", "British Columbia");
      states.put("CA", "California");
      states.put("CO", "Colorado");
      states.put("CT", "Connecticut");
      states.put("DE", "Delaware");
      states.put("DC", "District Of Columbia");
      states.put("FL", "Florida");
      states.put("GA", "Georgia");
      states.put("GU", "Guam");
      states.put("HI", "Hawaii");
      states.put("ID", "Idaho");
      states.put("IL", "Illinois");
      states.put("IN", "Indiana");
      states.put("IA", "Iowa");
      states.put("KS", "Kansas");
      states.put("KY", "Kentucky");
      states.put("LA", "Louisiana");
      states.put("ME", "Maine");
      states.put("MB", "Manitoba");
      states.put("MD", "Maryland");
      states.put("MA", "Massachusetts");
      states.put("MI", "Michigan");
      states.put("MN", "Minnesota");
      states.put("MS", "Mississippi");
      states.put("MO", "Missouri");
      states.put("MT", "Montana");
      states.put("NE", "Nebraska");
      states.put("NV", "Nevada");
      states.put("NB", "New Brunswick");
      states.put("NH", "New Hampshire");
      states.put("NJ", "New Jersey");
      states.put("NM", "New Mexico");
      states.put("NY", "New York");
      states.put("NF", "Newfoundland");
      states.put("NC", "North Carolina");
      states.put("ND", "North Dakota");
      states.put("NT", "Northwest Territories");
      states.put("NS", "Nova Scotia");
      states.put("NU", "Nunavut");
      states.put("OH", "Ohio");
      states.put("OK", "Oklahoma");
      states.put("ON", "Ontario");
      states.put("OR", "Oregon");
      states.put("PA", "Pennsylvania");
      states.put("PE", "Prince Edward Island");
      states.put("PR", "Puerto Rico");
      states.put("QC", "Quebec");
      states.put("RI", "Rhode Island");
      states.put("SK", "Saskatchewan");
      states.put("SC", "South Carolina");
      states.put("SD", "South Dakota");
      states.put("TN", "Tennessee");
      states.put("TX", "Texas");
      states.put("UT", "Utah");
      states.put("VT", "Vermont");
      states.put("VI", "Virgin Islands");
      states.put("VA", "Virginia");
      states.put("WA", "Washington");
      states.put("WV", "West Virginia");
      states.put("WI", "Wisconsin");
      states.put("WY", "Wyoming");
      states.put("YT", "Yukon Territory");
    }



    @GetMapping
    public String getAction( @ModelAttribute("command") PaymentInfo command, 
      Model model) {

      return "creditcards" ;

    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") PaymentInfo command,
      @RequestParam(value="action", required=true) String action,
      Errors errors, Model model, HttpServletRequest request) {


      log.info( "Action: " + action ) ;
      log.info( "Command: " + command ) ;

      CyberSourceAPI api = new CyberSourceAPI();
      CyberSourceAPI.setHost(apiHost);
      CyberSourceAPI.setKey(merchantKeyId);
      CyberSourceAPI.setSecret(merchantsecretKey);
      CyberSourceAPI.setMerchant(merchantId);
      CyberSourceAPI.debugConfig();

      ErrorMessages msgs = new ErrorMessages();

      boolean hasErrors = false;

      //validate empty field
      if(command.firstname().equals("")){
        hasErrors = true;
        msgs.add("First Name required.");
      }
      if(command.lastname().equals("")){
        hasErrors = true;
        msgs.add("Last Name required.");
      }
      if(command.address().equals("")){
        hasErrors = true;
        msgs.add(" Address required.");
      }
      if(command.city().equals("")){
        hasErrors = true;
        msgs.add("City required.");
      }
      if(command.state().equals("")){
        hasErrors = true;
        msgs.add("State required.");
      }
      if(command.zip().equals("")){
        hasErrors = true;
        msgs.add("Zip code required.");
      }
      if(command.phonenumber().equals("")){
        hasErrors = true;
        msgs.add("Phone number required.");
      }
      if(command.cardnum().equals("")){
        hasErrors = true;
        msgs.add("Card number required.");
      }
      if(command.expYear().equals("")){
        hasErrors = true;
        msgs.add("Expiration year required.");
      }
      if(command.expMon().equals("")){
        hasErrors = true;
        msgs.add("Expiration month required.");
      }
      if(command.cvv().equals("")){
        hasErrors = true;
        msgs.add("CVV required.");
      }
      if(command.email().equals("")){
        hasErrors = true;
        msgs.add("Email address required.");     
      }
      
      //validate format 
      if(!command.zip().matches("\\d{5}")){
        hasErrors = true;
        msgs.add("Invalid Zip code.");
      }
      if(!command.phonenumber().matches("[(]\\d{3}[)]\\d{3}-\\d{4}")){
        hasErrors = true;
        msgs.add("Invalid phone number");
      }
      if(!command.cardnum().matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")){
        hasErrors = true;
        msgs.add("Invalid card number.");
      }
      if(!command.expYear().matches("\\d{4}")){
        hasErrors = true;
        msgs.add("Invalid year.");
      }
      if(!command.cvv().matches("\\d{3}")){
        hasErrors = true;   
        msgs.add("Invalid card CVV.");
      }

      //validate month and state 
      if(months.get(command.expMon())== null){
        hasErrors = true;
        msgs.add("Invalid card expiration month.");
      }
      if(states.get(command.state())== null){
        hasErrors = true;
        msgs.add("Invalid state.");
      }

      if(hasErrors){
        msgs.print();
        model.addAttribute( "messages", msgs.getMessages()) ;
        return "creditcards";
      }


      int min = 1239871;
      int max = 9999999;
      int random = (int)Math.floor(Math.random()*(max-min+1)+min);
      String order_num = String.valueOf(random);
      AuthRequest auth = new AuthRequest() ;
      auth.reference = order_num;
      auth.billToFirstName = command.firstname();
      auth.billToLastName = command.lastname() ;
      auth.billToAddress = command.address() ;
      auth.billToCity = command.city() ;
      auth.billToState =command.state() ;
      auth.billToZipCode =command.zip();
      auth.billToPhone = command.phonenumber() ;
      auth.billToEmail = command.email() ;
      auth.transactionAmount = "25.00" ;
      auth.transactionCurrency = "USD" ;
      auth.cardNumnber = command.cardnum();
      auth.cardExpMonth = months.get(command.expMon());
      auth.cardExpYear = command.expYear() ;
      auth.cardCVV = command.cvv();
      auth.cardType = CyberSourceAPI.getCardType(auth.cardNumnber);
      if(auth.cardType.equals("ERROR")){
        System.out.println("Unsupported card type.");
        model.addAttribute("message","Unsupported card type.");
        return "creditcards";
      }

      boolean authValid = true ;
      AuthResponse authResponse = new AuthResponse() ;
      System.out.println("\n\nAuth Request: " + auth.toJson() ) ;
      authResponse = api.authorize(auth) ;  
      System.out.println("\n\nAuth Response: " + authResponse.toJson() ) ;
      if ( !authResponse.status.equals("AUTHORIZED") ) {
        authValid = false;
        System.out.println(authResponse.message);
        model.addAttribute("message",authResponse.message);
        return "creditcards";
      }

      boolean captureValid = true ;
      CaptureRequest capture = new CaptureRequest() ;
      CaptureResponse captureResponse = new CaptureResponse() ;
      if ( authValid ) {
        capture.reference = order_num ;
        capture.paymentId = authResponse.id ;
        capture.transactionAmount = "25.00" ;
        capture.transactionCurrency = "USD" ;
        System.out.println("\n\nCapture Request: " + capture.toJson() ) ;
        captureResponse = api.capture(capture) ;
        System.out.println("\n\nCapture Response: " + captureResponse.toJson() ) ;
        if ( !captureResponse.status.equals("PENDING") ) {
          captureValid = false ;
          System.out.println(captureResponse.message);
          model.addAttribute("message",captureResponse.message);
          return "creditcards";
        }

      }

      if(authValid && captureValid){
        command.setOrderNumber(order_num);
        command.setAmount("25.00");
        command.setCurrency("USD");
        command.setAuthID(authResponse.id);
        command.setAuthStatus(authResponse.status);
        command.setCapID(captureResponse.id);
        command.setCapStatus(captureResponse.status);

        repo.save(command);
        System.out.println("Thank you for you Payment. Order number is: "+ order_num);
        model.addAttribute("message","Thank you for you Payment.");
       

      }


      return "creditcards";

    }

  }