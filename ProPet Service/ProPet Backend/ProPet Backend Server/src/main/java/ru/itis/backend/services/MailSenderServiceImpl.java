package ru.itis.backend.services;

import com.squareup.okhttp.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.forms.ActivationMailForm;
import ru.itis.backend.exceptions.jwtserver.JwtRegistrationException;
import ru.itis.backend.security.managers.TokenManager;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    protected final OkHttpClient client;
    protected final TokenManager tokenManager;

    protected final String MAIL_SENDER_URL;
    protected final String CONFIRMATION_URL;

    @Autowired
    public MailSenderServiceImpl(
            TokenManager tokenManager,
            @Value("${mail-sender.server-host}") String serverHost,
            @Value("${mail-sender.server-port}") String serverPort,
            @Value("${mail-sender.confirm-mail-url}") String confirmationUrl
    ){
        client = new OkHttpClient();
        this.tokenManager = tokenManager;
        MAIL_SENDER_URL = serverHost + ":" + serverPort;
        CONFIRMATION_URL = confirmationUrl;

    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    public void sendConfirmMail(ActivationMailForm form) {
        try{
            JSONObject body = new JSONObject()
                    .put("login", form.getLogin())
                    .put("mail", form.getMail())
                    .put("activationLink", form.getActivationLink());
            RequestBody requestBody = RequestBody.create(JSON, body.toString());
            Request request = new Request.Builder()
                    .url(MAIL_SENDER_URL + CONFIRMATION_URL)
                    .addHeader("JWT", tokenManager.getAccessToken())
                    .post(requestBody)
                    .build();
            System.out.println(MAIL_SENDER_URL + CONFIRMATION_URL);
            Response response = client.newCall(request).execute();
            if (response.code() != 200){
                throw new JwtRegistrationException();
            }
        } catch (Exception ex) {
            throw new JwtRegistrationException(ex);
        }
    }
}
