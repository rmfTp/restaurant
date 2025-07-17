package org.koreait.admin.product.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.admin.product.constants.ProductStatus;
import org.koreait.file.entities.FileInfo;
import org.koreait.global.entities.BaseEntity;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@Entity
@Table(indexes = {
        @Index(name = "idx_product_created_at",columnList = "createdAt DESC"),
        @Index(name = "idx_product_name",columnList = "name"),
        @Index(name = "idx_sale_price",columnList = "salePrice")
})
public class Product extends BaseEntity {
    @Id
    @GeneratedValue
    private Long seq;

    @Column(length = 45, nullable = false)
    private String gid;

    @Column(length = 102, nullable = false)
    private String name;

    @Column(length = 60)
    private String category;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private int consumerPrice;

    private int salePrice;

    @Lob
    private String description;

    @Transient
    private List<FileInfo> listImages;
    @Transient
    private List<FileInfo> mainImages;
    @Transient
    private List<FileInfo> editorImages;

}