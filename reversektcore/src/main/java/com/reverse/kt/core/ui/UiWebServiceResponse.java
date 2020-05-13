package com.reverse.kt.core.ui;

import lombok.*;

/**
 * Created by vikas on 11-05-2020.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UiWebServiceResponse {
    private String imageFileSrc;
    private String msg;
}
