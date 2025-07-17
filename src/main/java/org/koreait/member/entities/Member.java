package org.koreait.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.global.entities.BaseEntity;
import org.koreait.member.constants.Authority;

import java.time.LocalDateTime;

@Data
@Entity
@Table(indexes = {
        @Index(name = "idx_member_created_at", columnList = "createdAt DESC"),
        @Index(name = "idx_member_name", columnList = "name"),
        @Index(name = "idx_member_mobile", columnList = "mobile")
})
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private Long seq;
    @Column(nullable = false, unique = true,length = 65)
    private String email;
    @Column(nullable = false, length = 105)
    private String password;
    @Column(nullable = false, length = 65)
    private String name;
    @Column(nullable = false, unique = true, length = 15)
    private String mobile;
    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.MEMBER;

    private boolean termsAgree;

    private boolean locked;
    private LocalDateTime expired;

    private LocalDateTime credentialChangedAt;
}