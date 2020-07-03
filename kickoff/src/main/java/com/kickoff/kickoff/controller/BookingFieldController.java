package com.kickoff.kickoff.controller;

import com.kickoff.kickoff.dao.BookingFieldRepo;
import com.kickoff.kickoff.dao.FieldRepo;
import com.kickoff.kickoff.dao.UserRepo;
import com.kickoff.kickoff.entity.BookingField;
import com.kickoff.kickoff.entity.Field;
import com.kickoff.kickoff.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bField")
@CrossOrigin
public class BookingFieldController {

    @Autowired
    private BookingFieldRepo bookingFieldRepo;

    @Autowired
    private FieldRepo fieldRepo;

    @Autowired
    private UserRepo userRepo;
    
    @GetMapping
    public Iterable<BookingField> getBookingField() {
        return bookingFieldRepo.findAll();
    }

    @GetMapping("/check/")
    public Iterable<BookingField> getDateTimeCheck(@RequestParam String date, @RequestParam String time, @RequestParam int field_id) {
        return bookingFieldRepo.findDateTime(date, time, field_id);
    }
    
    @PostMapping
    public BookingField addBookingField(@RequestBody BookingField bookingField) {
        return bookingFieldRepo.save(bookingField);
    }

    @PostMapping("/{fieldId}/{userId}")
    public BookingField addField(@RequestBody BookingField bookingField, @PathVariable int fieldId, @PathVariable int userId) {

        Field findField = fieldRepo.findById(fieldId).get();

        User findUser = userRepo.findById(userId).get();

        if (findField ==  null) 
            throw new RuntimeException("Field not found");

        if (findUser == null)
            throw new RuntimeException("user not found");
        

        bookingField.setField(findField);
        bookingField.setUser(findUser);
        return bookingFieldRepo.save(bookingField);
    }

    @GetMapping("/user/")
    public Iterable<BookingField> getFieldByProps(@RequestParam int user_id) {
        return bookingFieldRepo.findUserProps(user_id);
    }

    @DeleteMapping("/{id}")
    public void deleteBookingList(@PathVariable int id) {
        BookingField findBookingList =  bookingFieldRepo.findById(id).get();

        findBookingList.setField(null);
        findBookingList.setUser(null);

        bookingFieldRepo.deleteById(id);

    }

   

    


    
}