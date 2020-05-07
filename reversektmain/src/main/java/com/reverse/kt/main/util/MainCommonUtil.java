package com.reverse.kt.main.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by vikas on 21-04-2020.
 */
@Component
@Getter
@Setter(onMethod = @__(@Inject))
public class MainCommonUtil {

    private PasswordEncoder passwordEncoder;

    public String convertToBecrypt(String pwd){
        return passwordEncoder.encode(pwd);
    }
}
