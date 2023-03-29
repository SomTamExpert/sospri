package ch.bbw.pr.sospri.security;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class EncryptionService {

    private static final int workload = 10;
    String pepper = "pepper";
    int iterations = 1000;
    int hashWidth = 256;


    public String pbkdf2PasswordEncoder(String password) {
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(pepper, iterations, hashWidth);
        pbkdf2PasswordEncoder.setEncodeHashAsBase64(true);
        String pbksf2EncodedPassword = pbkdf2PasswordEncoder.encode(password);
        System.out.println("pbksf2EncodedPassword: " + pbksf2EncodedPassword);
        return pbksf2EncodedPassword;
    }

    public String bCryptPasswordEncoder(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(workload, new SecureRandom());
        String bCryptEncodedPassword = bCryptPasswordEncoder.encode(password);
        System.out.println("bCryptEncodedPassword: " + bCryptEncodedPassword);
        return bCryptEncodedPassword;
    }

}
