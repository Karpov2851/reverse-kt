package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.EmployeeMstrDao;
import com.reverse.kt.core.model.EmployeeMstr;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by vikas on 22-04-2020.
 */
@Service
@Setter(onMethod = @__(@Inject))
public class EmployeeService {

    private EmployeeMstrDao employeeMstrDao;

    public void createEmployeeRecord(EmployeeMstr e,boolean isInsert) throws Exception{
        employeeMstrDao.saveOrUpdateEntity(e,isInsert);
    }

    public EmployeeMstr fetchEmployeeForUserProfileSeq(int userProfileSeq) throws Exception{
        return employeeMstrDao.fetchEmployeeMstrForUserProfile(userProfileSeq);
    }
}
