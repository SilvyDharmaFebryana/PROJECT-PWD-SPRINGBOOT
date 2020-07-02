package com.kickoff.kickoff.controller;

import com.kickoff.kickoff.dao.PoolRepo;
import com.kickoff.kickoff.entity.Pool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kolam")
@CrossOrigin
public class PoolController {
    
    @Autowired
    private PoolRepo poolRepo;

    
    @GetMapping
    public Iterable<Pool> getPool() {
        return poolRepo.findAll();
    }



    

}