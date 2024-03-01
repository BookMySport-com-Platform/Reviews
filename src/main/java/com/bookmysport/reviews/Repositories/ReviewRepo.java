package com.bookmysport.reviews.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmysport.reviews.Models.ReviewDB;

@Repository
public interface ReviewRepo extends JpaRepository<ReviewDB, UUID> {
    
}
