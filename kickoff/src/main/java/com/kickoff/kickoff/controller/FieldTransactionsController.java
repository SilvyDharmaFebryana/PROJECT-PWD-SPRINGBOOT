package com.kickoff.kickoff.controller;

import com.kickoff.kickoff.dao.FieldTransactionsRepo;
import com.kickoff.kickoff.dao.UserRepo;
import com.kickoff.kickoff.entity.FieldTransactions;
import com.kickoff.kickoff.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class FieldTransactionsController {

    private String uploadPath = System.getProperty("user.dir") + "\\kickoff\\kickoff\\src\\main\\resources\\static\\buktiTransfer\\";

    @Autowired
    private FieldTransactionsRepo fieldTransactionsRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/{userId}")
    public FieldTransactions postingTransaction(@RequestBody FieldTransactions fieldTransactions, @PathVariable int userId) {

        User findUser = userRepo.findById(userId).get();

        if (findUser == null)
            throw new RuntimeException("USER NOT FOUND");

        fieldTransactions.setUser(findUser);
        return fieldTransactionsRepo.save(fieldTransactions);
    }

    @GetMapping
    public Iterable<FieldTransactions> getTransactions() {
        return fieldTransactionsRepo.findAll();
    }


    @GetMapping("/{ftId}")
    public FieldTransactions postingTransaction( @PathVariable int ftId) {

        FieldTransactions findFieldTransactions = fieldTransactionsRepo.findById(ftId).get();

        if (findFieldTransactions == null)
            throw new RuntimeException("TRANSACTION NOT FOUND");

        return findFieldTransactions;
    }

    @GetMapping("/pending")
    public Iterable<FieldTransactions> getPendingStatus(@RequestParam String status) {
        return fieldTransactionsRepo.statusPending("pending");
    }

    
    @GetMapping("/checkout/{idTrans}")
    public FieldTransactions addIdTransaction( @PathVariable int idTrans) {

        FieldTransactions findFieldTransactions = fieldTransactionsRepo.findById(idTrans).get();

        if (findFieldTransactions == null)
            throw new RuntimeException("TRANSACTION NOT FOUND");

        return findFieldTransactions;
    }

















}