package com.reverse.kt.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="EMPLOYEE_MSTR")
@Data
@NoArgsConstructor
public class EmployeeMstr extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EMPLOYEE_MSTR_SEQ")
    private Integer employeeMstrSeq;

    @ManyToOne
    @JoinColumn(name = "COMPANY_MSTR_SEQ")
    private CompanyMstr companyMstr;

    @OneToOne
    @JoinColumn(name = "USER_PROFILE_SEQ")
    private UserProfile userProfile;

    @OneToOne
    @JoinColumn(name = "BUSINESS_UNIT_SEQ")
    private ProjectItem projectItem;

    @Column(name = "EMPLOYEE_EMAIL",length = 50)
    private String employeeEmail;

    @Column(name = "EMPLOYEE_FIRST_NAME",length = 50)
    private String employeeFirstName;

    @Column(name = "EMPLOYEE_LAST_NAME",length = 50)
    private String employeeLastName;






}
