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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        return fieldService.getLapanganBasket("basket");
    }

    @GetMapping("/voli")
    public Iterable<Field> getLapanganVoli(@RequestParam String category) {
        return fieldRepo.findField("voli");
    }

    @GetMapping("/futsal")
    public Iterable<Field> getLapanganFutsal(@RequestParam String category) {
        return fieldRepo.findField("futsal");
    }

    @GetMapping("/tennis")
    public Iterable<Field> getLapanganTennis(@RequestParam String category) {
        return fieldRepo.findField("tennis");
    }

    @GetMapping("/badminton")
    public Iterable<Field> getLapanganBadminton(@RequestParam String category) {
        return fieldRepo.findField("badminton");
    }

    @GetMapping("/type")
    public Iterable<Field> getType(@RequestParam String type) {
        return fieldRepo.findType(type);
    }

    @GetMapping("/rate")
    public Iterable<Field> getRate(@RequestParam int satu, @RequestParam int dua) {
        return fieldRepo.rating(satu, dua);
    }

    @PutMapping("/edit/{id}")
    public String editLapangan(@RequestParam("file") Optional<MultipartFile> file, @RequestParam("lapanganEdit") String lapanganString, @PathVariable int id) throws JsonMappingException, JsonProcessingException {
		
		Field findField = fieldRepo.findById(id).get();
		
		findField = new ObjectMapper().readValue(lapanganString, Field.class);

		Date date = new Date();

		String fileDownloadUri = findField.getImage();

		if (file.toString() != "Optional.empty") {
			
			String fileExtension = file.get().getContentType().split("/")[1];

			String newFileName = "FIELD" + date.getTime() + "." + fileExtension;

			String fileName = StringUtils.cleanPath(newFileName);

			Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);

			try {
				Files.copy(file.get().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}

			fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/lapangan/download/")
					.path(fileName).toUriString();

		}

		findField.setImage(fileDownloadUri);
		fieldRepo.save(findField);
		
		return fileDownloadUri;
	
    } 
    
    @DeleteMapping("/{id}")
    public void deleteLapangan(@PathVariable int id) {
        
        Field findField = fieldRepo.findById(id).get();

        findField.setBookingField(null);
        // findField.set(null);

        fieldRepo.deleteById(id);

    }

    @GetMapping("/report/field")
    public Long getReportPaket(@RequestParam int id, @RequestParam String status) {
        return fieldRepo.reportField(id, "approve");
    }

    
    @GetMapping("/report/jumlah/pesan/")
    public Iterable<Field> getJumlahPesan() {
        return fieldRepo.getReportCount();
    }

    @GetMapping("/rating")
    public Iterable<Field> editRating(@RequestParam int id) {
        return fieldRepo.getLength(id);
    }

    @PutMapping("/rating/edit/{id}")
    public Field newRating(@PathVariable int id, @RequestBody Field field, @RequestParam Float newRating ) {

        Field findField = fieldRepo.findById(id).get();

        findField.setRating(newRating);
        return fieldRepo.save(findField);
    }


}