package com.kickoff.kickoff.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kickoff.kickoff.dao.UserRepo;
import com.kickoff.kickoff.entity.User;
import com.kickoff.kickoff.util.EmailUtil;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
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

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	private String uploadPath = System.getProperty("user.dir") + "\\kickoff\\kickoff\\src\\main\\resources\\static\\profile\\";
    
    @Autowired
    private UserRepo userRepo;

    private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    @Autowired
    private EmailUtil emailUtil;

    @PostMapping
	public User registerUser(@RequestBody User user) {

		String encodedPassword = pwEncoder.encode(user.getPassword());
		String verifyToken = pwEncoder.encode(user.getUsername() + user.getEmail());
		
		user.setPassword(encodedPassword);
		user.setVerified(false);

		// Simpan verifyToken di database
		user.setVerifyToken(verifyToken);
		
		User savedUser = userRepo.save(user);
		savedUser.setPassword(null);
		
		// Kirim verifyToken si user ke emailnya user
		String linkToVerify = "http://localhost:8081/users/verify/" + user.getUsername() + "?token=" + verifyToken;
		
		String message = "<h1>Selamat! Registrasi Berhasil</h1>\n";
		message += "Akun dengan username " + user.getUsername() + " telah terdaftar!\n";
		message += "Klik <a href=\"" + linkToVerify + "\">link ini</a> untuk verifikasi email anda.";
		
		emailUtil.sendEmail(user.getEmail(), "Registrasi Akun", message);
		
		return savedUser;
    }
    
    @GetMapping("/verify/{username}")
	public String verifyUserEmail (@PathVariable String username, @RequestParam String token) {
		User findUser = userRepo.findByUsername(username).get();
		
		if (findUser.getVerifyToken().equals(token)) {
			findUser.setVerified(true);
		} else {
			throw new RuntimeException("Token is invalid");
		}
		
		userRepo.save(findUser);

		// String home = "http://localhost:3000/";
		
		return "Sukses! akun anda telah terverifikasi\n";
	}

	@GetMapping("/username/email/")
    public Iterable<User> getUsername(@RequestParam String username, @RequestParam String email) {
        return userRepo.findUsername(username, email);
    }
	
	@GetMapping("/login")
    public User loginUser(@RequestParam String username, @RequestParam String password) {
		User findUser = userRepo.findByUsername(username).get();
		
        if (pwEncoder.matches(password, findUser.getPassword())) {
            findUser.setPassword(null);
            return findUser;
        } 

        throw new RuntimeException("wrong password");
	}

	@GetMapping("/login/byId")
    public Optional<User> loginById(@RequestParam int id) {
		return userRepo.findById(id);
	}


	@PostMapping("/edit/")
	public String editUser(@RequestParam("file") Optional<MultipartFile> file, @RequestParam(value ="userEdit") String userString, @RequestParam int userId) throws JsonMappingException, JsonProcessingException {
		
		User findUser = userRepo.findById(userId).get();
		
		findUser = new ObjectMapper().readValue(userString, User.class);

		Date date = new Date();

		String fileDownloadUri = findUser.getProfileImage();

		if (file.toString() != "Optional.empty") {
			
			String fileExtension = file.get().getContentType().split("/")[1];

			String newFileName = "USER" + date.getTime() + "." + fileExtension;

			String fileName = StringUtils.cleanPath(newFileName);

			Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);

			try {
				Files.copy(file.get().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}

			fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/download/")
					.path(fileName).toUriString();

		}

		findUser.setProfileImage(fileDownloadUri);
		// findUser.setId(userId);
		userRepo.save(findUser);
		
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