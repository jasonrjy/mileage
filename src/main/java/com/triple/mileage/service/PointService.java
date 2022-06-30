package com.triple.mileage.service;

import com.triple.mileage.entity.tUser;
import com.triple.mileage.error.CustomException;
import com.triple.mileage.error.ErrorCode;
import com.triple.mileage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PointService {
    private final UserRepository userRepository;

    public int getUserPoint(String userid) {
        Optional<tUser> _user = this.userRepository.findById(userid);

        if (_user.isPresent()) {
            return this.userRepository.selectUserPoint(userid);
        } else {
            throw new CustomException(ErrorCode.NOT_EXISTS_USER);
        }

    }
}
