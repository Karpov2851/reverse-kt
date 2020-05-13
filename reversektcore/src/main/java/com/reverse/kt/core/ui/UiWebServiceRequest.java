package com.reverse.kt.core.ui;

import lombok.*;

import java.util.Map;

/**
 * Created by vikas on 09-05-2020.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UiWebServiceRequest {

    private Map<String,Object> companyRelatedParams;
    private String companyFetchOps;
}
