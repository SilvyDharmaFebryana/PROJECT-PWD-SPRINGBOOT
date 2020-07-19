package com.kickoff.kickoff.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // agar column id auto increment dgn otomatis
    private int id;
    private String category;
    private String type;
    private double price;
    private String description;
    private String image;
    private String fieldName;
    private float rating;
    private int jumlah;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "field", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BookingField> bookingField;

    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldTransactions", cascade = CascadeType.ALL)
    // @JsonIgnore
    // private List<FTransactionDetails> fTransactionDetails;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "field", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notif> notif;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<BookingField> getBookingField() {
        return bookingField;
    }

    public void setBookingField(List<BookingField> bookingField) {
        this.bookingField = bookingField;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public List<Notif> getNotif() {
        return notif;
    }

    public void setNotif(List<Notif> notif) {
        this.notif = notif;
    }

  
}
