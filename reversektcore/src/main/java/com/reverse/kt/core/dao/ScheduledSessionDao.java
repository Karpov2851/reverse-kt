package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.ScheduledSession;
import com.reverse.kt.core.model.ScheduledSessionUser;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikas on 04-06-2020.
 */
@Repository("scheduledSessionDao")
public class ScheduledSessionDao extends GenericDao<ScheduledSession,Integer>{

    public List<ScheduledSession> fetchScheduledSessionForUser(int userProfileSeq) throws Exception{
        Root<ScheduledSession> scheduledSessionRoot = getRoot();
        Join<ScheduledSession,ScheduledSessionUser> scheduledSessionUserJoin = scheduledSessionRoot.join("scheduledSessionUsers");
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(getCriteriaBuilder().equal(scheduledSessionUserJoin.get("userProfile"), userProfileSeq));
        setRoot(scheduledSessionRoot);
        return findListOfEntireRecordsBasedOnCriteria(conditions.toArray(new Predicate[] {}));
    }
}
