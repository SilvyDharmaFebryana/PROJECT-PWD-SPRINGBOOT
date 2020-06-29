package com.kickoff.kickoff.controller;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kickoff.kickoff.dao.FieldRepo;
import com.kickoff.kickoff.entity.Field;
import com.kickoff.kickoff.service.FieldService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@RestController
@RequestMapping("/lapangan")
@CrossOrigin
public class FieldController {

    private String uploadPath = System.getProperty("user.dir") + "\\kickoff\\kickoff\\src\\main\\resources\\static\\images\\";

    @Autowired
    private FieldRepo fieldRepo;

    @Autowired
    private FieldService fieldService;

    @GetMapping
    public Iterable<Field> getLapangan() {
        return fieldRepo.findAll();
    }

    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fieldData") String fieldString) throws JsonMappingException, JsonProcessingException {

        Date date = new Date();

        Field field = new ObjectMapper().readValue(fieldString, Field.class);

        String fileExtension = file.getContentType().split("/")[1];
        String newFileName = "FIELD-IMG-" + date.getTime() + "." + fileExtension;

        String fileName = StringUtils.cleanPath(newFileName);

        Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return fileName + " has been upload";

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/lapangan/download/")
                .path(fileName).toUriString();

        field.setImage(fileDownloadUri);
        fieldRepo.save(field);
        
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

    
    // @PostMapping
    // public Field addField(@RequestBody Field field){
    //     return fieldService.addLapangan(field);
    // }

    @GetMapping("/{id}")
    public Optional<Field> getLapanganById(@PathVariable int id) {
        return fieldService.getLapanganById(id);
    }

    @GetMapping("/basket")
    public Iterable<Field> getLapanganBasket(@RequestParam String category) {
        return fieldRepo.findField("basket");
    }

    @GetMapping("/voli")
    public Iterable<Field> getLapanganVoli(@RequestParam String category) {
        return fieldRepo.findField("voli");
    }

    @GetMapping("/futsal")
    public Iterable<Field> getLapanganFutsal(@RequestParam String category) {
        return fieldRepo.findField("futsal");
    }




}