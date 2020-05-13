package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.ProjectDao;
import com.reverse.kt.core.dao.ProjectItemDao;
import com.reverse.kt.core.model.Project;
import com.reverse.kt.core.model.ProjectItem;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by vikas on 09-05-2020.
 */
@Service("projectService")
@Setter(onMethod = @__(@Inject))
public class ProjectService {

    private ProjectDao projectDao;

    private ProjectItemDao projectItemDao;

    public List<Project> fetchProjectForBusinessUnit(Integer buSeq) throws Exception{
        return projectDao.fetchProjectForBusinessUnit(buSeq);
    }

    public Project fetchProjectForId(Integer projectSeq) throws Exception{
        return projectDao.findById(projectSeq);
    }

    public ProjectItem fetchProjectItemForProjectItemSeq(int projectItemSeq) throws Exception{
        return projectItemDao.findById(projectItemSeq);
    }
}
