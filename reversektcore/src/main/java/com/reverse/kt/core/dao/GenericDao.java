package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.BaseEntity;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;

/**
 * Created by vikas on 02-04-2020.
 */
@Transactional
public abstract class GenericDao<T extends BaseEntity, PK> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> persistanceClass;

    @SuppressWarnings("unchecked")
    public GenericDao() {
        this.persistanceClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Class<T> getEntityClass(){
        return persistanceClass;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public T findById(Integer id) {
        return (T) entityManager.find(this.persistanceClass, id);
    }


    public void saveOrUpdateEntity(final T bean, final boolean isInsert) {
        Session session = entityManager.unwrap(Session.class);
        if (isInsert) {
            initInsert(bean);
            session.save(bean);
        } else {
            initUpdate(bean);
            session.merge(bean);
        }
    }


    private void initInsert(final T bean) {
        bean.setCreatedDate(LocalDateTime.now());
        bean.setUpdatedDate(LocalDateTime.now());
        bean.setCreatedBy(0);
        bean.setUpdatedBy(0);
    }

    private void initUpdate(final T bean) {
        bean.setUpdatedDate(LocalDateTime.now());
    }


}
