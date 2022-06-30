package com.triple.mileage.repository;

import com.triple.mileage.entity.tUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<tUser, String> {

    @Transactional
    @Modifying
    @Query("UPDATE tUser SET point = point + :variation WHERE id = :id")
    void updatePoint(@Param("id") String id, @Param("variation") Integer variation);

    @Query("SELECT point FROM tUser WHERE id = :userId")
    int selectUserPoint(@Param("userId") String userId);
}
