package ch.bbw.km.winthi.Recaptcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ReCaptchaValidationService {

    @Autowired
    private ReCaptchaSettings reCaptchaSettings;

    public static String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean validateCaptcha(String captchaResponse) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", reCaptchaSettings.getSecret());
        requestMap.add("response", captchaResponse);

        RecaptchaResponseType apiResponse = restTemplate.postForObject(RECAPTCHA_URL, requestMap, RecaptchaResponseType.class);
        if (apiResponse == null) {
            return false;
        }
        return Boolean.TRUE.equals(apiResponse.isSuccess());
    }
}
