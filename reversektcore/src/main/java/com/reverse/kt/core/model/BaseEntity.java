package com.reverse.kt.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Created by vikas on 02-04-2020.
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @Column(name="CREATED_BY")
    private Integer createdBy = 0;
    @Column(name="UPDATED_BY")
    private Integer updatedBy = 0;

    @Column(name="CREATED_DATE")
    @Basic
    private LocalDateTime createdDate;
    @Column(name="UPDATED_DATE")

    @Basic
    private LocalDateTime updatedDate;

    @Column(name="STATUS")
    private Character status;


}
