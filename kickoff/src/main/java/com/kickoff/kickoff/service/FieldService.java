package com.kickoff.kickoff.service;

import java.util.Optional;

import com.kickoff.kickoff.controller.FieldController;
import com.kickoff.kickoff.entity.Field;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface FieldService {
    
    public Field addLapangan(Field field); 

    public Optional<Field> getLapanganById(int id);

	public Iterable<Field> getLapanganBasket(String category);
}