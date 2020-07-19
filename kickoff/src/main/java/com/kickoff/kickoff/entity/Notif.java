package com.kickoff.kickoff.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Notif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // agar column id auto increment dgn otomatis
    private int id;
    private String notif;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "field_id")
    private Field field;

    // @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    // @JoinColumn(name = "ftransaction_details_id")
    // private FTransactionDetails fTransactionDetails;


    @OneToOne(cascade = CascadeType.ALL) // untuk join table
    @JoinColumn(name = "ftransaction_details_id") // nama fk
    private FTransactionDetails fTransactionDetails; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotif() {
        return notif;
    }

    public void setNotif(String notif) {
        this.notif = notif;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FTransactionDetails getfTransactionDetails() {
        return fTransactionDetails;
    }

    public void setfTransactionDetails(FTransactionDetails fTransactionDetails) {
        this.fTransactionDetails = fTransactionDetails;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    // public FTransactionDetails getfTransactionDetails() {
    //     return fTransactionDetails;
    // }

    // public void setfTransactionDetails(FTransactionDetails fTransactionDetails) {
    //     this.fTransactionDetails = fTransactionDetails;
    // }


    
}