package org.koreait.restaurant.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(indexes = {

})
public class Restaurant {
    @Id
    @GeneratedValue
    private Long seq;
    @Column(length = 20)
    private String zipcode;
    @Column(length = 150)
    private String address;
    @Column(length = 20)
    private String zonecode;
    @Column(length = 150)
    private String roadAddress;
    @Column(length = 30)
    private String category;
    @Column(length = 80)
    private String name;
    private double lat;
    private double lon;
}
