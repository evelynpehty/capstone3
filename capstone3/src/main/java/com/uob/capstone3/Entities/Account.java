package com.uob.capstone3.Entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Account")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountID;

    @Column(nullable = false) 
    private String customerNRIC;

    @Column(nullable = false) 
    private String customerName;

    @ManyToOne 
    @JoinColumn(name = "accountType_id", nullable = false)
    private AccountType accountType;

    @Column(nullable = false, columnDefinition = "DATE DEFAULT SYSDATE") 
    private LocalDate accountCreatedDate;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 1")
    private int accountIsActive;

    @Column(nullable = false, columnDefinition = "NUMERIC(10, 2) DEFAULT 0.00") 
    private Double accountBalance;

}
