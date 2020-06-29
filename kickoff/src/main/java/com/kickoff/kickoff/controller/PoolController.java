package com.kickoff.kickoff.controller;

import com.kickoff.kickoff.dao.PoolRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kolam")
@CrossOrigin
public class PoolController {
    
    @Autowired
    private PoolRepo poolRepo;

    

}