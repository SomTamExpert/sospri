package ch.bbw.pr.sospri.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
public class EncryptionService {

    private static final int workload = 10;

    public String bCryptPasswordEncoder(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(workload, new SecureRandom());
        try {
            String bCryptEncodedPassword = bCryptPasswordEncoder.encode(password);
            log.info("bCryptEncodedPassword: " + bCryptEncodedPassword);
            return bCryptEncodedPassword;
        } catch (Exception e) {
            log.error("Error while encoding password: " + e.getMessage());
            return null;
        }
    }
}
