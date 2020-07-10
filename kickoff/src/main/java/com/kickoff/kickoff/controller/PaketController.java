package com.kickoff.kickoff.controller;

import com.kickoff.kickoff.dao.PaketRepo;
import com.kickoff.kickoff.entity.Paket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paket")
@CrossOrigin
public class PaketController {
    

    @Autowired
    private PaketRepo paketRepo;

    @GetMapping
    public Iterable<Paket> getLapangan() {
        return paketRepo.findAll();
    }

    @PostMapping
    public Paket addPaket(@RequestBody Paket paket) {
        return paketRepo.save(paket);
    }
}