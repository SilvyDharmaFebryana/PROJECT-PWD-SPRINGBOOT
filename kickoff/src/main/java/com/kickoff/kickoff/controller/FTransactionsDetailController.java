package com.kickoff.kickoff.controller;

import com.kickoff.kickoff.dao.FTransactionDetailsRepo;
import com.kickoff.kickoff.dao.FieldRepo;
import com.kickoff.kickoff.dao.FieldTransactionsRepo;
import com.kickoff.kickoff.entity.FTransactionDetails;
import com.kickoff.kickoff.entity.Field;
import com.kickoff.kickoff.entity.FieldTransactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction/details")
@CrossOrigin
public class FTransactionsDetailController {
    
    @Autowired
    private FTransactionDetailsRepo fTransactionDetailsRepo;

    @Autowired
    private FieldRepo fieldRepo;

    @Autowired
    private FieldTransactionsRepo fieldTransactionsRepo;

    
    @PostMapping("/{fieldId}/{fieldTransactionId}")
    public FTransactionDetails postingTransactionDetails(@RequestBody FTransactionDetails fTransactionsDetails, @PathVariable int fieldId, @PathVariable int fieldTransactionId) {

        // User findUser = userRepo.findById(userId).get();

        // if (findUser == null)
        //     throw new RuntimeException("USER NOT FOUND");

        // fieldTransactions.setUser(findUser);
        // return fieldTransactionsRepo.save(fieldTransactions);

        Field findField = fieldRepo.findById(fieldId).get();

        FieldTransactions findFieldTransactions = fieldTransactionsRepo.findById(fieldTransactionId).get();

        if ( findField == null )
            throw new RuntimeException("FIELD NOT FOUND");

        if ( findFieldTransactions == null )
            throw new RuntimeException("TRANSACTION NOT VALID");

        fTransactionsDetails.setField(findField);
        fTransactionsDetails.setFieldTransactions(findFieldTransactions);
        return fTransactionDetailsRepo.save(fTransactionsDetails);
        

    }

}