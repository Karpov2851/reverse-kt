package com.reverse.kt.main.processor;

import com.reverse.kt.core.model.SkillMstr;
import com.reverse.kt.main.service.SkillService;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by vikas on 01-07-2020.
 */
@Component
@Setter(onMethod = @__(@Inject))
@Transactional
public class ProjectProcessor {

    private SkillService skillService;

    public Map<String,Integer> fetchSkillsDropDownForProjectItemSeq(Integer projectItemSeq) throws Exception{
        List<SkillMstr> skillMstrList = skillService.fetchSkillsForProjectItemSeq(projectItemSeq);
        return Optional.ofNullable(skillMstrList).orElseGet(Collections::emptyList).stream()
                .collect(Collectors.toMap(SkillMstr::getSkillDesc,SkillMstr::getSkillMstrSeq));
    }
}
