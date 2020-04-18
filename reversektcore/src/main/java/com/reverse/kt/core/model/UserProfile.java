package com.reverse.kt.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    public UserProfile(UserRole userRole,CompanyMstr companyMstr,String userId,String password,Integer createdBy, Integer updatedBy, Date createdDate, Date updatedDate){
        super(createdBy,updatedBy,createdDate,updatedDate,'A');
        this.userRole = userRole;
        this.companyMstr = companyMstr;
        this.userId = userId;
        this.password = password;
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

    @Column(name = "PASSWORD",length = 50)
    private String password;
}
