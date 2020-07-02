package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.ScheduledSession;
import com.reverse.kt.core.model.ScheduledSessionUser;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by vikas on 04-06-2020.
 */
@Repository("scheduledSessionDao")
public class ScheduledSessionDao extends GenericDao<ScheduledSession,Integer>{

    public List<ScheduledSession> fetchScheduledSessionForUser(int userProfileSeq) throws Exception{
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ScheduledSession> scheduledSessionCriteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<ScheduledSession> scheduledSessionRoot = scheduledSessionCriteriaQuery.from(getEntityClass());
        Join<ScheduledSession,ScheduledSessionUser> scheduledSessionUserJoin = scheduledSessionRoot.join("scheduledSessionUsers");
        scheduledSessionCriteriaQuery.where(criteriaBuilder.equal(scheduledSessionUserJoin.get("userProfile"),userProfileSeq));
        return getEntityManager().createQuery(scheduledSessionCriteriaQuery).getResultList();
    }
}
