package com.triple.mileage.service;

import com.triple.mileage.entity.Photo;
import com.triple.mileage.entity.PointHistory;
import com.triple.mileage.entity.Review;
import com.triple.mileage.error.CustomException;
import com.triple.mileage.error.ErrorCode;
import com.triple.mileage.model.EventRequest;
import com.triple.mileage.repository.PhotoRepository;
import com.triple.mileage.repository.PointHistoryRepository;
import com.triple.mileage.repository.ReviewRepository;
import com.triple.mileage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventService {
    private final ReviewRepository reviewRepository;
    private final PhotoRepository photoRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final UserRepository userRepository;

    public Review getReview(String id) {
        Optional<Review> review = this.reviewRepository.findById(id);
        if (review.isPresent()) {
            return review.get();
        } else {
            throw new CustomException(ErrorCode.NOT_EXISTS_REVIEW);
        }
    }

    @Transactional
    public void createEntity(EventRequest params) {
        Review review = params.toReviewEntity();
        List<Photo> photo = params.toPhotoEntity();
        PointHistory pointHistory = params.toPointHistoryEntity();

        int _point = 0;

        // 보너스 점수
        Optional<List<Review>> _isReview = this.reviewRepository.selectByPlaceIdAndStatusNot(review.getPlace().getId(), "deleted");
        if (!_isReview.isPresent() || _isReview.get().size() == 0) _point += 1;
        else {
            Optional<List<Review>> _isUser = this.reviewRepository.selectByPlaceIdAndUserIdAndStatusNot(
                    review.getPlace().getId(), review.getUser().getId(), "deleted");
            if (_isUser.isPresent() && _isUser.get().size() >= 1) {
                throw new CustomException(ErrorCode.DUPLICATED_REVIEW);
            }
        }


        // 내용 점수
        if (review.getContent().length() != 0) _point += 1;
        else throw new CustomException(ErrorCode.NOT_EXISTS_REVIEW_CONTENTS);

        // 사진 점수
        if (photo.size() >= 1) _point += 1;

        review.setPoint(_point);
        this.reviewRepository.save(review);

        pointHistory.setVariation(_point);
        this.pointHistoryRepository.save(pointHistory);

        this.userRepository.updatePoint(review.getUser().getId(), _point);

        if (photo.size() >= 1) this.photoRepository.saveAll(photo);
    }

    @Transactional
    public void modifyEntity(EventRequest params) {
        int prev_point = -1;
        int now_point = -1;
        Review prev_review;

        Review review = params.toReviewEntity();
        List<Photo> photoList = params.toPhotoEntity();
        PointHistory pointHistory = params.toPointHistoryEntity();

        Optional<Review> _prev_review = this.reviewRepository.findById(review.getId());


        if (_prev_review.isPresent()) {
            prev_review = _prev_review.get();
            prev_point = prev_review.getPoint();
            now_point = prev_point;

            if (prev_review.getStatus() != null && prev_review.getStatus().equals("deleted")) {
                throw new CustomException(ErrorCode.ALREADY_DELETED);
            }
            Optional<List<Photo>> _photo = this.photoRepository.findByReviewId(review.getId());

            // 사진 처리
            if (_photo.isPresent() && _photo.get().size() >= 1) {
                this.photoRepository.deleteAll(_photo.get());
                now_point -= 1;
            }
            if (photoList.size() >= 1) {
                now_point += 1;
                this.photoRepository.saveAll(photoList);
            }

            // 내용 처리
            if (review.getContent().length() == 0) {
                throw new CustomException(ErrorCode.NOT_EXISTS_REVIEW_CONTENTS);
            } else {
                this.reviewRepository.modifyReview(review.getId(), review.getContent(), now_point);
            }

            if (now_point != prev_point) {
                pointHistory.setVariation(now_point - prev_point);
                this.pointHistoryRepository.save(pointHistory);
                this.userRepository.updatePoint(review.getUser().getId(),now_point - prev_point );
            }

        } else {
            throw new CustomException(ErrorCode.NOT_EXISTS_REVIEW);
        }

    }

    @Transactional
    public void deleteEntity(EventRequest params) {
        Review review = params.toReviewEntity();
        PointHistory pointHistory = params.toPointHistoryEntity();

        Optional<Review> _target_review = this.reviewRepository.findById(review.getId());

        if (_target_review.isPresent()) {
            // 사진 삭제
            Optional<List<Photo>> _photo = this.photoRepository.findByReviewId(review.getId());
            _photo.ifPresent(this.photoRepository::deleteAll);

            // 포인트 삭제
            pointHistory.setVariation(_target_review.get().getPoint() * -1);
            this.pointHistoryRepository.save(pointHistory);
            this.userRepository.updatePoint(review.getUser().getId(),
                    _target_review.get().getPoint() * -1 );

            this.reviewRepository.deleteReview(_target_review.get().getId());

        } else {
            throw new CustomException(ErrorCode.NOT_EXISTS_REVIEW);
        }

    }
}
