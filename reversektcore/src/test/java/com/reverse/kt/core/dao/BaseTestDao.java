package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.BaseEntity;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

/**
 * Created by vikas on 04-04-2020.
 */
public abstract class BaseTestDao<E extends BaseEntity,T extends GenericDao,PE extends Serializable> {

    protected abstract E getSingleEntity();
    protected abstract T getDao();
    @Test
    public abstract void verifySave();

    @Before
    public void genericBaseDaoTest(){
        saveEntity();
    }

    protected E findById(Integer id){
        return (E)getDao().findById(id);
    }

    protected void saveEntity(){
        getDao().saveOrUpdateEntity(getSingleEntity(),true);
    }
}
