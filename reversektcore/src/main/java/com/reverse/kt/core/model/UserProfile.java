package com.reverse.kt.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="USER_PROFILE")
@Getter
@Setter
@NoArgsConstructor
public class UserProfile extends BaseEntity{

    @Builder
    public UserProfile(UserRole userRole, CompanyMstr companyMstr, String userProfileImagePath, String userId,
                       String password, Integer createdBy, Integer updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate){
        super(createdBy,updatedBy,createdDate,updatedDate,'A');
        this.userRole = userRole;
        this.companyMstr = companyMstr;
        this.userId = userId;
        this.password = password;
        this.userProfileImagePath = userProfileImagePath;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_PROFILE_SEQ")
    private Integer userProfileSeq;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ROLE_SEQ")
    private UserRole userRole;

    @ManyToOne
    @JoinColumn(name = "COMPANY_MSTR_SEQ")
    private CompanyMstr companyMstr;

    @Column(name = "USER_ID",length = 50)
    private String userId;

    @Column(name = "USER_PROFILE_IMAGE_PATH")
    private String userProfileImagePath;

    @Column(name = "USER_PROFILE_IMAGE_REF_CD",length = 300)
    private String userProfileImageRefCd;

    @Column(name = "PASSWORD")
    private String password;
}
