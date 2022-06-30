package com.triple.mileage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.triple.mileage.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@ToString
public class EventRequest {
    @JsonProperty("type")
    private String type;

    @JsonProperty("action")
    private EventAction action;

    @JsonProperty("reviewId")
    private String reviewId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("attachedPhotoIds")
    private ArrayList<String> attachedPhotoIds;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("placeId")
    private String placeId;

    public Review toReviewEntity() {
        Review _review = Review.builder().id(reviewId).build();
        tUser user = tUser.builder().id(userId).build();
        Place place = Place.builder().id(placeId).build();

        System.out.println("In EventReq" + user.toString() + place.toString());

        return Review.builder()
                .id(reviewId).content(content)
                .place(place).user(user)
                .build();
    }

    public List<Photo> toPhotoEntity() {
        Review _review = Review.builder().id(reviewId).build();

        List<Photo> photo = new ArrayList<>();
        attachedPhotoIds.forEach(photoId -> {
            photo.add(Photo.builder().id(photoId).review(_review).build());
        });

        return photo;

    }

    public PointHistory toPointHistoryEntity() {
        tUser user = tUser.builder().id(userId).build();
        LocalDateTime date = LocalDateTime.now();

        return PointHistory.builder()
                .user(user)
                .time(date)
                .reason(type + "_" + action)
                .target(reviewId)
                .build();
    }
}
