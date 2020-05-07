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

    private boolean showError;
    private boolean showSuccess;
    private String message;
    private String showSection;
}
