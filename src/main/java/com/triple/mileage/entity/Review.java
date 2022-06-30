package com.triple.mileage.entity;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Entity
@Table(name="review"
//    ,uniqueConstraints = {
//        @UniqueConstraint(
//                name="UserPlaceUnique",
//                columnNames={"place_id", "user_id"}
//        )
//    }
)
@Data
@Builder
@NoArgsConstructor
@ToString
public class Review {
    @Id
    private String id;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private tUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(nullable = true)
    private String status; // 삭제, 수정

    @Column(columnDefinition = "INT default 0")
    private Integer point;



    // foreign
    @Builder.Default
    @OneToMany(mappedBy = "review")
    private List<Photo> photoList = new ArrayList<>();


    @Builder
    public Review(String id, String content, tUser user,
                  Place place, String status, Integer point, List<Photo> photos) {

//        Assert.notNull(id, "reviewId must not be null");
//        Assert.notNull(user.getId(), "userId must not be null");
//        Assert.notNull(place.getId(), "placeId must not be null");
//        Assert.notNull(content, "content must not be null");
//        Assert.notNull(point, "point must not be null");
//        Assert.notNull(photos, "photoList must not be null");

        this.id = id;
        this.content = content;
        this.user = user;
        this.place = place;
        this.status = status;
        this.photoList = photos;
        this.point = point;
    }

//    public int modify(String content, int photoChange) {
//        int point = this.point;
//        if (this.content.length() == 0 && content.length() >= 1) {
//            point += 1;
//        }
//        if (photoChange == 1) {
//            point += 1;
//        } else if (photoChange == -1) {
//            point -= 1;
//        }
//        this.content = content;
//        this.point = point;
//
//        return point;
//    }


}
