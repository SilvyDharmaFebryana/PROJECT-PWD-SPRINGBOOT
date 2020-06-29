package com.kickoff.kickoff.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // agar column id auto increment dgn otomatis
    private int id;
    private String poolType;
    private double poolPrice;
    private String poolDescription;
    private String poolImage;
    private String fieldName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoolType() {
        return poolType;
    }

    public void setPoolType(String poolType) {
        this.poolType = poolType;
    }

    public double getPoolPrice() {
        return poolPrice;
    }

    public void setPoolPrice(double poolPrice) {
        this.poolPrice = poolPrice;
    }

    public String getPoolDescription() {
        return poolDescription;
    }

    public void setPoolDescription(String poolDescription) {
        this.poolDescription = poolDescription;
    }

    public String getPoolImage() {
        return poolImage;
    }

    public void setPoolImage(String poolImage) {
        this.poolImage = poolImage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    

}