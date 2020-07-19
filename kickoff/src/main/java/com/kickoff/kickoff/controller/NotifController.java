package com.kickoff.kickoff.controller;

import com.kickoff.kickoff.dao.FTransactionDetailsRepo;
import com.kickoff.kickoff.dao.FieldRepo;
import com.kickoff.kickoff.dao.NotifRepo;
import com.kickoff.kickoff.dao.UserRepo;
import com.kickoff.kickoff.entity.FTransactionDetails;
import com.kickoff.kickoff.entity.Field;
import com.kickoff.kickoff.entity.Notif;
import com.kickoff.kickoff.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notif")
@CrossOrigin
public class NotifController {
    

    @Autowired
    private NotifRepo notifRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FieldRepo fieldRepo;

    @Autowired
    private FTransactionDetailsRepo fTransactionDetailsRepo;

    @PostMapping("/{userId}/{transIdDet}/{fieldId}")
    public Notif postingNotif(@PathVariable int userId, @PathVariable int transIdDet, @PathVariable int fieldId, @RequestBody Notif notif) {
        
        User findUser = userRepo.findById(userId).get();

        if ( findUser == null )
            throw new RuntimeException("USER NOT FOUND");

        FTransactionDetails findTransDetail = fTransactionDetailsRepo.findById(transIdDet).get();

        Field findField = fieldRepo.findById(fieldId).get();

        // notif.setFui
        notif.setField(findField);
        notif.setfTransactionDetails(findTransDetail);
        notif.setUser(findUser);
        return notifRepo.save(notif);
    }

    @GetMapping("/")
    public Iterable<Notif> getUserIdNotif(@RequestParam int userId) {

        User findUser = userRepo.findById(userId).get();

        if ( findUser == null )
            throw new RuntimeException("USER NOT FOUND");


        return notifRepo.findNotifUser(userId);
    }


    // @DeleteMapping("/{id}")
    // public void deleteBookingList(@PathVariable int id) {
    //     BookingField findBookingList =  bookingFieldRepo.findById(id).get();

    //     findBookingList.setField(null);
    //     findBookingList.setUser(null);

    //     bookingFieldRepo.deleteById(id);

    // }


    @DeleteMapping("/{id}")
    public void deleteNotif(@PathVariable int id) {

        Notif findNotif = notifRepo.findById(id).get(); 

        findNotif.setField(null);
        findNotif.setUser(null);
        findNotif.setfTransactionDetails(null);

        notifRepo.deleteById(id);
    }

    

}