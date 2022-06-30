package com.triple.mileage.entity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="place")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    @Id
    private String id;

    private String name;

    // foreign
    @OneToMany(mappedBy = "place")
    private List<Review> reviewList = new ArrayList<>();

    @Builder
    public Place(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
