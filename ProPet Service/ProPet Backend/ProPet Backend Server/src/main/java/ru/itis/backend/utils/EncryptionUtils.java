package ru.itis.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {

    protected static PasswordEncoder encoder;

    @Autowired
    public EncryptionUtils(PasswordEncoder encoder){
        EncryptionUtils.encoder = encoder;
    }

    public static String encryptPassword(String password){
        return encoder.encode(password);
    }

}
