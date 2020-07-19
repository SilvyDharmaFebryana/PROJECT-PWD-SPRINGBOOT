package com.kickoff.kickoff.service.Impl;

import java.util.Optional;

import javax.transaction.Transactional;

import com.kickoff.kickoff.dao.FieldRepo;
import com.kickoff.kickoff.entity.Field;
import com.kickoff.kickoff.service.FieldService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldServiceImpl implements FieldService {

    // @Autowired
    // private Field field;

    @Autowired
    private FieldRepo fieldRepo;
    
    @Override
    @Transactional
    public Field addLapangan(Field field) {
        field.setId(0);
        return fieldRepo.save(field);
    }


    @Override
    @Transactional
	public Optional<Field> getLapanganById(int id) {
		return fieldRepo.findById(id);
    }
    
    

    @Override
    @Transactional
    public Iterable<Field> getLapanganBasket(String category) {
        return fieldRepo.findField(category);
    }
}