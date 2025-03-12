package com.eazybytes.eazyschool.bean;

import com.eazybytes.eazyschool.annotation.FieldsValueMatch;
import com.eazybytes.eazyschool.annotation.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
public class Profile{


    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message = "Address field cannot be empty")
    @Size(min = 5, message = "Address1 must be at least 5 characters long")
    private String address1;

    private String address2;

    @NotBlank(message = "City field cannot be empty")
    @Size(min=3, message = "City must be at least 3 characters long")
    private String city;

    @NotBlank(message = "State cannot be empty")
    @Size(min=5, message = "State must be at least 5 characters long")
    private String state;

    @NotBlank(message = "Zipcode cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{6})",message = "zipcode must be at least of 6 digits")
    private String zipCode;


}