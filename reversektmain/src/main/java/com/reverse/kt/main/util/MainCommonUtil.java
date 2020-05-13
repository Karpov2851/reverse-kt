package com.reverse.kt.main.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import java.security.Key;

/**
 * Created by vikas on 21-04-2020.
 */
@Component
@Getter
@Setter(onMethod = @__(@Inject))
public class MainCommonUtil {
    private static final String ALGO = "AES";
    private static final String AES_KEY = "REVERSELOCKDOWN1";

    private PasswordEncoder passwordEncoder;

    public String decryptEncrypt(String text,int cipher) throws Exception{
        String value = "";
        byte[] byteValue = null;
        Key key = new SecretKeySpec(AES_KEY.getBytes(), ALGO);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(cipher, key);
        if(cipher == Cipher.ENCRYPT_MODE){
            byteValue = c.doFinal(text.getBytes());
            value = new BASE64Encoder().encode(byteValue);
        }else if(cipher == Cipher.DECRYPT_MODE){
            byte[] baseDecode = new BASE64Decoder().decodeBuffer(text);
            byteValue = c.doFinal(baseDecode);
            value = new String(byteValue);
        }
        return value;
    }
    public String convertToBecrypt(String pwd){
        return passwordEncoder.encode(pwd);
    }

}
