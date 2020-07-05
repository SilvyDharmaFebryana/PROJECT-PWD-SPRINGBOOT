package com.kickoff.kickoff.controller;

import java.util.Optional;

import com.kickoff.kickoff.dao.UserRepo;
import com.kickoff.kickoff.entity.User;
import com.kickoff.kickoff.util.EmailUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    
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



	
}