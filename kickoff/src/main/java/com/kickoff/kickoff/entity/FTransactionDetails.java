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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FTransactionDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    private String bookingDate;
    private String time;
    private double totalPrice;
    private int duration;
    private String kodeBooking;
    private boolean isCheckin;


    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "field_transactions_id")
    private FieldTransactions fieldTransactions;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "field_id")
    private Field field;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "user_id")
    private User user;

    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "fTransactionDetails", cascade = CascadeType.ALL)
    // @JsonIgnore
    // private List<Notif> notif;

    @OneToOne(mappedBy = "fTransactionDetails", fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE,
        CascadeType.PERSIST, CascadeType.REFRESH })
    @JsonIgnore
    private Notif notif;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public FieldTransactions getFieldTransactions() {
        return fieldTransactions;
    }

    public void setFieldTransactions(FieldTransactions fieldTransactions) {
        this.fieldTransactions = fieldTransactions;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getKodeBooking() {
        return kodeBooking;
    }

    public void setKodeBooking(String kodeBooking) {
        this.kodeBooking = kodeBooking;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isCheckin() {
        return isCheckin;
    }

    public void setCheckin(boolean isCheckin) {
        this.isCheckin = isCheckin;
    }

    // public List<Notif> getNotif() {
    //     return notif;
    // }

    // public void setNotif(List<Notif> notif) {
    //     this.notif = notif;
    // }


}