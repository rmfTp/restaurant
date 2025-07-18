package org.koreait.trend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Trend_url")
public class TrendUrl {
    @Id
    @Column(length = 150)
    private String siteUrl;
}