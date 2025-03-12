package com.eazybytes.eazyschool.bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.bind.annotation.RequestParam;

@Data//to avoid getters and setters and toString methods
@Entity
@Table(name = "contact_msg")
@NamedQueries({
        @NamedQuery(name = "ContactBean.findOpenMsgs",query="SELECT c from ContactBean c WHERE c.status = :status"),
        @NamedQuery(name = "ContactBean.updateMsgStatus",query="UPDATE ContactBean c SET c.status=:status WHERE c.contactId=:contactId")
})
public class ContactBean extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name = "contact_id")
    private int contactId;

    @NotBlank(message="Name must not be blank")
    @Size(min = 3, message="Name must be atleast 3 characters long")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Enter a 10 digit mobile number ")
    private String mobileNum;

    @NotBlank(message = "Email must not be blank" )
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Subject must not be blank" )
    @Size(min = 5, message="Subject must be atleast 5 characters long")
    private String subject;

    @NotBlank(message = "message must not be blank" )
    @Size(min = 10, message="message must be atleast 10 characters long")
    private String message;


    private String status;
}
