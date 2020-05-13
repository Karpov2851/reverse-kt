package com.reverse.kt.main.processor;

import com.reverse.kt.core.model.BusinessUnit;
import com.reverse.kt.core.model.Project;
import com.reverse.kt.core.model.ProjectItem;
import com.reverse.kt.core.model.UserProfile;
import com.reverse.kt.main.service.ProjectService;
import com.reverse.kt.main.service.UserService;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by vikas on 09-05-2020.
 */
@Component
@Setter(onMethod = @__(@Inject))
@Transactional
public class CompanyProcessor {

    public static enum OperationOps{
        BU,PROJ,PROJITEM,SKILL;
        public static OperationOps checkEnumValue(String val) throws IllegalArgumentException{
            return Arrays.stream(OperationOps.values())
                    .filter(e -> e.name().equals(val))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", val)));
        }
    }
    private UserService userService;
    private ProjectService projectService;

    public Map<Object,Object> fetchCompanyRelatedEntity(Integer userProfileSeq,Map<String,Object> mapper,CompanyProcessor.OperationOps op) throws Exception{
        Map<Object,Object> dataMap = new HashMap<>();
        UserProfile userProfile = userService.fetchUserByProfileSeq(userProfileSeq);
        if(userProfile.getCompanyMstr()!=null){
            switch (op){
                case PROJ:
                    BusinessUnit bu = userProfile.getCompanyMstr().getBusinessUnits().stream().
                            filter(t->t.getBusinessCd().equalsIgnoreCase(String.valueOf(mapper.get("bu")))).findFirst().orElseThrow(IllegalArgumentException::new);
                    List<Project> projectsList = projectService.fetchProjectForBusinessUnit(bu.getBusinessUnitSeq());
                    dataMap = projectsList.stream().collect(Collectors.toMap(Project::getProjectSeq,Project::getProjectName));
                    break;
                case PROJITEM:
                    Project project = userProfile.getCompanyMstr().getProjects().stream().
                            filter(t->t.getProjectSeq().equals((int)mapper.get("projectSeq"))).findFirst().orElseThrow(IllegalArgumentException::new);
                    dataMap = project.getProjectItems().stream().collect(Collectors.toMap(ProjectItem::getProjectItemSeq,ProjectItem::getProjectItemDesc));
                    break;
            }
        }else{
            throw new IllegalArgumentException("Company record not found");
        }
        return dataMap;
    }
}
