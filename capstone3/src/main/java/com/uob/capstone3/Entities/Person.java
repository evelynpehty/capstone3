package com.uob.capstone3.Entities;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "Person")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false) 
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false) 
    private String lastName;

    @Column(nullable = false) 
    private String role; //Admin or Teller

    @Column(nullable = false, columnDefinition = "DATE DEFAULT SYSDATE")
    @Temporal(TemporalType.DATE) 
    private LocalDate creationDate;

}
