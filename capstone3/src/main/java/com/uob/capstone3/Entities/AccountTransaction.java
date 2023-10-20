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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AccountTransaction")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionID;

    @ManyToOne 
    @JoinColumn(name = "accountID", nullable = false)
    private Account accountID;

    @Column(nullable = false)
    private String transactionType;

    @Column(nullable = true)
    private String transactionDescription;

    @Column(nullable = false)
    private Double transactionAmount;

    @Column(nullable = false, columnDefinition = "DATE DEFAULT SYSDATE")
    @Temporal(TemporalType.DATE) 
    private LocalDate transactionDate;

    @ManyToOne 
    @JoinColumn(name = "transactionPartyAccountID", nullable = true)
    private Account transactionPartyAccountID; 
    
}
