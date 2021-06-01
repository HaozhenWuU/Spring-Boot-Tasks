package com.example.springpayments;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;  
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Payment")
@Data
@RequiredArgsConstructor
class PaymentInfo {

	private @Id @GeneratedValue Long id;

	@NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String address;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull(message = "Please enter 5 digits zipcode")
    // @Pattern(regexp = "^[0-9]{5}")
    private String zip ;
    @NotNull
    // @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
    private String phonenumber ;
    @NotNull
    // @Pattern(regexp = "^\\d{4}[- .]?\\d{4}[- .]?\\d{4}[- .]?\\d{4}$")
    private String cardnum ;
    @NotNull
    // @Size(min = 4, max = 4, message = "Expiration year should be 4 digits")
    private String expYear ;
    @NotNull
    private String expMon ;
    @NotNull
    // @Size(min = 3, max = 3, message = "Card CVV should be 3 digits")
    private String cvv;
    @NotNull
    private String email;

    private String notes ;

    
    private String orderNumber;
    private String amount;
    private String currency;
    private String authID;
    private String authStatus;
    private String capID;
    private String capStatus;

    public String firstname(){
        return firstname;
    }
    public String lastname(){
        return lastname;
    }
    public String address(){
        return address;
    }

    public String city(){
        return city;
    }

    public String state(){
        return state;
    }

    public String zip(){
        return zip;
    }

    public String phonenumber(){
        return phonenumber;
    }

    public String cardnum(){
        return cardnum;
    }

    public String expYear(){
        return expYear;
    }

    public String expMon(){
        return expMon;
    }

    public String cvv(){
        return cvv;
    }

    public String email(){
        return email;
    }

    public String note(){
        return notes;
    }
}
