package com.kickoff.kickoff.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kickoff.kickoff.dao.FieldTransactionsRepo;
import com.kickoff.kickoff.dao.PaketRepo;
import com.kickoff.kickoff.dao.UserRepo;
import com.kickoff.kickoff.entity.FTransactionDetails;
import com.kickoff.kickoff.entity.FieldTransactions;
import com.kickoff.kickoff.entity.Paket;
import com.kickoff.kickoff.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class FieldTransactionsController {

    private String uploadPath = System.getProperty("user.dir") + "\\kickoff\\kickoff\\src\\main\\resources\\static\\bukti\\";

    @Autowired
    private FieldTransactionsRepo fieldTransactionsRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PaketRepo paketRepo;

    @PostMapping("/{userId}/{paketId}")
    public FieldTransactions postingTransaction(@RequestBody FieldTransactions fieldTransactions, @PathVariable int userId, @PathVariable int paketId) {

        User findUser = userRepo.findById(userId).get();

        if (findUser == null)
            throw new RuntimeException("USER NOT FOUND");

        Paket findPaket = paketRepo.findById(paketId).get();

        if (findPaket == null)
            throw new RuntimeException("USER NOT FOUND");

        fieldTransactions.setUser(findUser);
        fieldTransactions.setPaket(findPaket);
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
        findFieldTransactions.setMessage("transaksi sukses");
        return fieldTransactionsRepo.save(findFieldTransactions);
    }

    @PutMapping("/admin/decline/{idTrans}")
    public FieldTransactions declineTrans(@PathVariable int idTrans, @RequestBody FieldTransactions fieldTransactions){

        FieldTransactions findFieldTransactions = fieldTransactionsRepo.findById(idTrans).get();
    
        findFieldTransactions.setStatus("decline");
        findFieldTransactions.setApproveDate("belom approve");
        return fieldTransactionsRepo.save(findFieldTransactions);
    }

    @PutMapping("/admin/failed/{idTrans}")
    public FieldTransactions failedTrans(@PathVariable int idTrans, @RequestBody FieldTransactions fieldTransactions){

        FieldTransactions findFieldTransactions = fieldTransactionsRepo.findById(idTrans).get();
    
        findFieldTransactions.setStatus("failed");
        findFieldTransactions.setApproveDate("ditolak");
        return fieldTransactionsRepo.save(findFieldTransactions);
    }

    
    @PutMapping("/admin/done/{idTrans}")
    public FieldTransactions doneTrans(@PathVariable int idTrans, @RequestBody FieldTransactions fieldTransactions){

        FieldTransactions findFieldTransactions = fieldTransactionsRepo.findById(idTrans).get();
    
        findFieldTransactions.setStatus("done");
        return fieldTransactionsRepo.save(findFieldTransactions);
    }



    @GetMapping("/pagination")
    public Page<FieldTransactions> getTransactions(Pageable pageable) {
        return fieldTransactionsRepo.findAll(pageable);
    }

    @GetMapping("/{ftId}")
    public FieldTransactions postingTransaction( @PathVariable int ftId) {

        FieldTransactions findFieldTransactions = fieldTransactionsRepo.findById(ftId).get();

        if (findFieldTransactions == null)
            throw new RuntimeException("TRANSACTION NOT FOUND");

        return findFieldTransactions;
    }

    @GetMapping("/pending/admin")
    public Iterable<FieldTransactions> adminTask(@RequestParam String status) {
        return fieldTransactionsRepo.statusForAdmin("pending");
    }

    @GetMapping("/attempt")
    public Iterable<FieldTransactions> getAttempt(@RequestParam int attempt) {
        return fieldTransactionsRepo.getAttempt(2);
    }

    @GetMapping("/none")
    public Iterable<FieldTransactions> getNoPayment(@RequestParam String status, @RequestParam int user_id) {
        return fieldTransactionsRepo.status("noPayment", user_id);
    }

    @GetMapping("/pending")
    public Iterable<FieldTransactions> getPendingStatus(@RequestParam String status, @RequestParam int user_id) {
        return fieldTransactionsRepo.status("pending", user_id);
    }

    @GetMapping("/sukses")
    public Iterable<FieldTransactions> getSuksesStatus(@RequestParam String status, @RequestParam int user_id) {
        return fieldTransactionsRepo.status("approve", user_id);
    }

    @GetMapping("/decline")
    public Iterable<FieldTransactions> getDeclineStatus(@RequestParam String status, @RequestParam int user_id) {
        return fieldTransactionsRepo.status("decline", user_id);
    }

    @GetMapping("/gagal")
    public Iterable<FieldTransactions> getGagalStatus(@RequestParam String status, @RequestParam int user_id) {
        return fieldTransactionsRepo.status("failed", user_id);
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

    
    @PutMapping("/{id}")
	public String editTransa(@RequestParam("file") MultipartFile file, @RequestParam("transData") String transString, @PathVariable int id) throws JsonMappingException, JsonProcessingException {
		
		FieldTransactions findFieldTransactions = fieldTransactionsRepo.findById(id).get();
		
		
		findFieldTransactions = new ObjectMapper().readValue(transString, FieldTransactions.class);
		Date date = new Date();
		
		String fileExtension = file.getContentType().split("/")[1];
		String newFileName = "BUKTI-TF" + date.getTime() + "." + fileExtension;
		
		String fileName = StringUtils.cleanPath(newFileName);
		
		Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);
		
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/transaction/download/")
				.path(fileName).toUriString();
		
		
        findFieldTransactions.setBuktiTransfer(fileDownloadUri);
        findFieldTransactions.setAttempt(2);
        findFieldTransactions.setStatus("pending");
		fieldTransactionsRepo.save(findFieldTransactions);
		
		return fileDownloadUri;
    }





















}