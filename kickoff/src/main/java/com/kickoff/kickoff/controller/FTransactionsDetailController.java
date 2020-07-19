package com.kickoff.kickoff.controller;

import java.util.List;

import com.kickoff.kickoff.dao.FTransactionDetailsRepo;
import com.kickoff.kickoff.dao.FieldRepo;
import com.kickoff.kickoff.dao.FieldTransactionsRepo;
import com.kickoff.kickoff.dao.UserRepo;
import com.kickoff.kickoff.entity.FTransactionDetails;
import com.kickoff.kickoff.entity.Field;
import com.kickoff.kickoff.entity.FieldTransactions;
import com.kickoff.kickoff.entity.User;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    @Autowired
    private UserRepo userRepo;

    @GetMapping("/trans/{idTrans}")
    public Iterable<FTransactionDetails> getDetailsFrom(@PathVariable int idTrans) {
        
        FieldTransactions findFieldTransactions = fieldTransactionsRepo.findById(idTrans).get();

        return fTransactionDetailsRepo.findDetails(findFieldTransactions);
    }

    @GetMapping("/check/")
    public Iterable<FTransactionDetails> getDateTimeFieldCheck(@RequestParam String booking_date, @RequestParam String time, @RequestParam int field_id) {
        return fTransactionDetailsRepo.findDateTimeField(booking_date, time, field_id);
    }
    
    @PostMapping("/{fieldId}/{fieldTransactionId}/{userId}")
    public FTransactionDetails postingTransactionDetails (
        @RequestBody FTransactionDetails fTransactionsDetails, 
        @PathVariable int fieldId, 
        @PathVariable int fieldTransactionId,
        @PathVariable int userId
    ) {

        User findUser = userRepo.findById(userId).get();

        Field findField = fieldRepo.findById(fieldId).get();

        FieldTransactions findFieldTransactions = fieldTransactionsRepo.findById(fieldTransactionId).get();

        if ( findField == null )
            throw new RuntimeException("FIELD NOT FOUND");

        if ( findFieldTransactions == null )
            throw new RuntimeException("TRANSACTION NOT VALID");

        fTransactionsDetails.setField(findField);
        fTransactionsDetails.setFieldTransactions(findFieldTransactions);
        fTransactionsDetails.setUser(findUser);
        return fTransactionDetailsRepo.save(fTransactionsDetails);
        
    }

    @GetMapping("/admin/report/day/")
    public List<FTransactionDetails> getReportDay(@RequestParam String status, @RequestParam int field_id ) {
        return fTransactionDetailsRepo.reportDay("approve", field_id);
    }

    @GetMapping("/admin/report/day/date")
    public List<FTransactionDetails> getReportDayWithDate(@RequestParam String status, @RequestParam int field_id, @RequestParam String booking_date, Pageable pageable ) {
        return fTransactionDetailsRepo.reportDayWithDate("approve", field_id, booking_date, pageable);
    }

    @GetMapping("/admin/") 
    public Iterable<FTransactionDetails> getAll(Pageable pageable) {
        return fTransactionDetailsRepo.findAll(pageable);
    }

    @GetMapping("/validasi/")
    public Iterable<FTransactionDetails> getAllBookList(@RequestParam String booking_date, @RequestParam String status) {
        return fTransactionDetailsRepo.todayCheckin(booking_date, "approve");
    }

    @GetMapping("/validasi/render")
    public Iterable<FTransactionDetails> render() {
        return fTransactionDetailsRepo.renderCheck();
    }
    
    @PutMapping("/validasi/checkin/{idTrans}")
    public FTransactionDetails validasiChekin(@PathVariable int idTrans, @RequestBody FTransactionDetails fTransactionsDetails) {

            FTransactionDetails findTransDetail = fTransactionDetailsRepo.findById(idTrans).get();

            findTransDetail.setCheckin(true);
            return fTransactionDetailsRepo.save(findTransDetail);    
    }


}