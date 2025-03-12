package com.eazybytes.eazyschool.bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "class")
public class EazyClass extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int classId;

    @NotBlank(message = "Name field cannot be empty")
    @Size(min = 3, message = "Name must contain atleast 3 characters")
    private String name;

    @OneToMany(mappedBy = "eazyClass", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, targetEntity = Person.class)
    private Set<Person> persons;

}
