package com.reverse.kt.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="USER_ROLE")
@Getter
@Setter
@NoArgsConstructor
public class UserRole extends BaseEntity{

    @Builder
    public UserRole(Integer userRoleSeq,CompanyMstr companyMstr,String userRoleDesc,String userRoleCd,
                    Integer createdBy, Integer updatedBy, Date createdDate, Date updatedDate){
        super(createdBy,updatedBy,createdDate,updatedDate,'A');
        this.userRoleSeq = userRoleSeq;
        this.companyMstr = companyMstr;
        this.userRoleDesc = userRoleDesc;
        this.userRoleCd = userRoleCd;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ROLE_SEQ")
    private Integer userRoleSeq;

    @ManyToOne
    @JoinColumn(name = "COMPANY_MSTR_SEQ")
    private CompanyMstr companyMstr;

    @OneToMany(mappedBy = "userRole")
    private Set<UserProfile> userProfileList;

    @Column(name = "USER_ROLE_DESC",length = 50)
    private String userRoleDesc;

    @Column(name = "USER_ROLE_CD",length = 50)
    private String userRoleCd;
}
