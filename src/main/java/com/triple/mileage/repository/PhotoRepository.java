package com.triple.mileage.repository;

import com.triple.mileage.entity.Photo;
import com.triple.mileage.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, String> {

    Optional<List<Photo>> findByReviewId(String reviewId);
}
