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
@Table(name="EMPLOYEE_MSTR")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeMstr extends BaseEntity{

    @Builder
    public EmployeeMstr(CompanyMstr companyMstr, UserProfile userProfile, BusinessUnit businessUnit, ProjectItem projectItem,
                        String employeeEmail, String employeeFirstName, String employeeLastName,
                        Integer createdBy, Integer updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate){
        super(createdBy,updatedBy,createdDate,updatedDate,'A');
        this.companyMstr = companyMstr;
        this.userProfile = userProfile;
        this.businessUnit = businessUnit;
        this.projectItem = projectItem;
        this.employeeEmail = employeeEmail;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EMPLOYEE_MSTR_SEQ")
    private Integer employeeMstrSeq;

    @ManyToOne
    @JoinColumn(name = "COMPANY_MSTR_SEQ")
    private CompanyMstr companyMstr;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_PROFILE_SEQ")
    private UserProfile userProfile;

    @OneToOne
    @JoinColumn(name = "BUSINESS_UNIT_SEQ")
    private BusinessUnit businessUnit;

    @OneToOne
    @JoinColumn(name = "PROJECT_ITEM_SEQ")
    private ProjectItem projectItem;

    @OneToOne
    @JoinColumn(name = "DESIGNATION_MSTR_SEQ")
    private DesignationMstr designationMstr;

    @Column(name = "EMPLOYEE_EMAIL",length = 50)
    private String employeeEmail;

    @Column(name = "EMPLOYEE_FIRST_NAME",length = 50)
    private String employeeFirstName;

    @Column(name = "EMPLOYEE_LAST_NAME",length = 50)
    private String employeeLastName;






}
