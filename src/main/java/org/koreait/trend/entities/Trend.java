package org.koreait.trend.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.global.entities.BaseEntity;

@Data
@Entity
public class Trend extends BaseEntity {
    @Id
    @GeneratedValue
    private long seq;

    @Column(length = 60)
    private String category;
    @Lob
    private String keywords;
    @Lob
    private String wordCloud;
}
