package com.kickoff.kickoff.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kickoff.kickoff.dao.FieldTransactionsRepo;
import com.kickoff.kickoff.dao.UserRepo;
import com.kickoff.kickoff.entity.FTransactionDetails;
import com.kickoff.kickoff.entity.FieldTransactions;
import com.kickoff.kickoff.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class FieldTransactionsController {

    private String uploadPath = System.getProperty("user.dir") + "\\kickoff\\kickoff\\src\\main\\resources\\static\\bukti\\";

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

    @PutMapping("/admin/approve/{idTrans}")
    public FieldTransactions approveTrans(@PathVariable int idTrans, @RequestBody FieldTransactions fieldTransactions){

        FieldTransactions findFieldTransactions = fieldTransactionsRepo.findById(idTrans).get();
        
        // let date = new D+ate();
        Date date = new Date();
        String approveDate = date.toLocaleString();

        findFieldTransactions.setStatus("approve");
        findFieldTransactions.setApproveDate(approveDate);
        return fieldTransactionsRepo.save(findFieldTransactions);
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

    @GetMapping("/none")
    public Iterable<FieldTransactions> getNoPayment(@RequestParam String status) {
        return fieldTransactionsRepo.status("noPayment");
    }

    @GetMapping("/pending")
    public Iterable<FieldTransactions> getPendingStatus(@RequestParam String status) {
        return fieldTransactionsRepo.status("pending");
    }

    @GetMapping("/sukses")
    public Iterable<FieldTransactions> getSuksesStatus(@RequestParam String status) {
        return fieldTransactionsRepo.status("sukses");
    }

    @GetMapping("/gagal")
    public Iterable<FieldTransactions> getGagalStatus(@RequestParam String status) {
        return fieldTransactionsRepo.status("gagal");
    }
    
    @GetMapping("/checkout/{idTrans}")
    public FieldTransactions addIdTransaction( @PathVariable int idTrans) {

        FieldTransactions findFieldTransactions = fieldTransactionsRepo.findById(idTrans).get();

        if (findFieldTransactions == null)
            throw new RuntimeException("TRANSACTION NOT FOUND");

        return findFieldTransactions;
    }

    @PutMapping("/checkout/upload_file/{id}")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fieldData") String fieldString, @PathVariable int id ) throws JsonMappingException, JsonProcessingException {

        Date date = new Date();

        FieldTransactions fieldTransactions = fieldTransactionsRepo.findById(id).get();

        fieldTransactions = new ObjectMapper().readValue(fieldString, FieldTransactions.class);

        String fileExtension = file.getContentType().split("/")[1];
        String newFileName = "BUKTI-TF-" + date.getTime() + "." + fileExtension;

        String fileName = StringUtils.cleanPath(newFileName);

        Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return fileName + " has been upload";

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/transaction/download/")
                .path(fileName).toUriString();

        fieldTransactions.setAttempt(1);
        fieldTransactions.setStatus("pending");
        fieldTransactions.setBuktiTransfer(fileDownloadUri);
        fieldTransactionsRepo.save(fieldTransactions);
        
        
        return fileDownloadUri;
    }


    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName) {
        Path path = Paths.get(uploadPath, fileName);

        Resource resource = null;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream")).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + resource.getFilename() + "\"").body(resource);
    }





















}