package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.BaseEntity;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

/**
 * Created by vikas on 02-04-2020.
 */
@Transactional
public abstract class GenericDao<T extends BaseEntity,PK> {

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaQuery<T> criteriaQuery;

    private CriteriaBuilder criteriaBuilder;

    private final Class<T> persistanceClass;

    private Root<T> root;


    @SuppressWarnings("unchecked")
    public GenericDao(){
        this.persistanceClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @PostConstruct
    public void setupResources(){
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.criteriaQuery = this.criteriaBuilder.createQuery(persistanceClass);
        this.root = this.criteriaQuery.from(persistanceClass);
    }



    public T findById(Integer id){
        return (T)entityManager.find(this.persistanceClass,id);
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    public Root<T> getRoot() {
        return root;
    }

    public CriteriaQuery<T> getCriteriaQuery() {
        return criteriaQuery;
    }

    public void saveOrUpdateEntity(final T bean, final boolean isInsert){
        Session session = entityManager.unwrap(Session.class);
        if(isInsert){
            initInsert(bean);
            session.save(bean);
        }else{
            initUpdate(bean);
            session.merge(bean);
        }
    }

    public List<T> findListOfEntireRecordsBasedOnCriteria(Predicate[] predicate){
        this.criteriaQuery.select(this.root);
        criteriaQuery.where(predicate);
        TypedQuery<T> queryBasedOnPredicate = entityManager.createQuery(criteriaQuery);
        return (List<T>)queryBasedOnPredicate.getResultList();
    }


    private void initInsert(final T bean) {
        final Date d = new Date();
        bean.setCreatedDate(d);
        bean.setUpdatedDate(d);
    }

    private void initUpdate(final T bean) {
        bean.setUpdatedDate(new Date());
    }



}