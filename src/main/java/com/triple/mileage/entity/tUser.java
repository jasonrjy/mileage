package com.triple.mileage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class tUser {
    @Id
    private String id;

    private String password;

    @Column(unique=true)
    private String email;

    @Column(columnDefinition = "INT default 0")
    private Integer point;

    // foreign
    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();

    @Builder
    public tUser(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }

}
