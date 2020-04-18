package com.reverse.kt.core.ui;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by vikas on 18-04-2020.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseModelView {

    private String showError;
    private String showSuccess;
    private String showSection;
}
