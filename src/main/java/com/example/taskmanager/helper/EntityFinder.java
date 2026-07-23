package com.example.taskmanager.helper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class EntityFinder {

    public static <T>T findOrThrow(JpaRepository<T,Long> jpaRepository, Long id,String entityName) {
        if(id<=0){
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        return jpaRepository.findById(id).orElseThrow(()->new EntityNotFoundException(entityName+" with id: "+id+" does not exists"));
    }
}
