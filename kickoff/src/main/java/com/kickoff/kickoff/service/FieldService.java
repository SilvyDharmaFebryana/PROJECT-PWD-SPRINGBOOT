package com.kickoff.kickoff.service;

import java.util.Optional;

import com.kickoff.kickoff.entity.Field;

public interface FieldService {
    
    public Field addLapangan(Field field); 

    public Optional<Field> getLapanganById(int id);
}