package com.eazybytes.eazyschool.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Address extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int addressId;

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
    @Pattern(regexp = "(^&|[0-9]{6})",message = "zipcode must be at least of 6 digits")
    private String zipCode;
}
