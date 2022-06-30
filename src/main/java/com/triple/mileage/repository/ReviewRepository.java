package com.triple.mileage.repository;

import com.triple.mileage.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Optional<Review> findById(String id);
//    Optional<List<Review>> findByPlaceId(String placeId);
//    Optional<Review> findByPlaceIdAndUserId(String PlaceId, String userId);
//    Optional<List<Review>> findByPlaceIdAndUserIdAndStatusNot(String placeId, String userId, String status);
//
//    boolean existsByPlaceId(String placeId);
//    boolean existsByPlaceIdAndUserId(String placeId, String userId);
//    boolean existsByPlaceIdAndStatusNot(String id, String status);
//    boolean existsByPlaceIdAndUserIdAndStatusNot(String placeId, String userId, String status);

    @Query(value = "SELECT * FROM Review WHERE place_id = :place_id and (status != :status or status is NULL)", nativeQuery = true)
    Optional<List<Review>> selectByPlaceIdAndStatusNot(@Param("place_id") String place_id, @Param("status") String status);

    @Query(value = "SELECT * FROM Review WHERE place_id = :place_id and user_id = :user_id and (status != :status or status is NULL)", nativeQuery = true)
    Optional<List<Review>> selectByPlaceIdAndUserIdAndStatusNot(
            @Param("place_id") String place_id, @Param("user_id") String user_id, @Param("status") String status);

    @Transactional
    @Modifying
    @Query("UPDATE Review r SET r.content = :content, r.status = 'modify', r.point = :point WHERE r.id = :id")
    int modifyReview(@Param("id") String id, @Param("content") String content, @Param("point") Integer point);

    @Transactional
    @Modifying
    @Query("UPDATE Review r SET r.status = 'deleted' WHERE r.id = :id")
    int deleteReview(@Param("id") String id);


}
