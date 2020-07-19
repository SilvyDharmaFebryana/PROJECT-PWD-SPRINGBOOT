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
public class Paket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // agar column id auto increment dgn otomatis
    private int id;
    private String jenisPaket;
    private String namaPaket;
    private int jumlah;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paket", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<FieldTransactions> fieldTransactions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJenisPaket() {
        return jenisPaket;
    }

    public void setJenisPaket(String jenisPaket) {
        this.jenisPaket = jenisPaket;
    }

    public String getNamaPaket() {
        return namaPaket;
    }

    public void setNamaPaket(String namaPaket) {
        this.namaPaket = namaPaket;
    }

    public List<FieldTransactions> getFieldTransactions() {
        return fieldTransactions;
    }

    public void setFieldTransactions(List<FieldTransactions> fieldTransactions) {
        this.fieldTransactions = fieldTransactions;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }


}