package com.kickoff.kickoff.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FieldTransactions {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // agar column id auto increment dgn otomatis
    private int id;
    private double totalPrice;
    private double totalDuration;
    private String status;
    private String paymentMethod;
    private String checkoutDate;
    private String approveDate;
    private String buktiTransfer;
    private String noPesanan;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldTransactions", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<FTransactionDetails> fTransactionDetails;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getBuktiTransfer() {
        return buktiTransfer;
    }

    public void setBuktiTransfer(String buktiTransfer) {
        this.buktiTransfer = buktiTransfer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNoPesanan() {
        return noPesanan;
    }

    public void setNoPesanan(String noPesanan) {
        this.noPesanan = noPesanan;
    }

    // public List<FTransactionDetails> getFieldTransactionDetails() {
    //     return fTransactionDetails;
    // }

    // public void setFieldTransactionDetails(List<FTransactionDetails> fieldTransactionDetails) {
    //     this.fTransactionDetails = fieldTransactionDetails;
    // }

    

}