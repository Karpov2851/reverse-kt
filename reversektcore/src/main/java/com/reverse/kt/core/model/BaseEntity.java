package com.reverse.kt.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vikas on 02-04-2020.
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @Column(name="CREATED_BY")
    private Integer createdBy;
    @Column(name="UPDATED_BY")
    private Integer updatedBy;

    @Column(name="CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name="UPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name="STATUS")
    private Character status;


}
