package ru.itis.backend.services;

import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.forms.JwtUpdateForm;
import ru.itis.backend.dto.forms.TokenRegistrationForm;
import ru.itis.backend.exceptions.jwtserver.JwtRegistrationException;
import ru.itis.backend.exceptions.jwtserver.JwtUpdateException;
import ru.itis.backend.security.managers.TokenManager;

@Service
@RequiredArgsConstructor
public class JwtModuleServiceImpl implements JwtModuleService {

    protected final OkHttpClient client;
    protected final TokenManager tokenManager;

    protected final String TOKEN_SERVER_URL;
    protected final String REGISTRATION_URL;
    protected final String UPDATE_URL;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Autowired
    public JwtModuleServiceImpl(
            TokenManager tokenManager,
            @Value("${security.jwt.token-server-host}") String serverHost,
            @Value("${security.jwt.token-server-port}") String serverPort,
            @Value("${security.jwt.registration-url}") String registrationUrl,
            @Value("${security.jwt.update-url}") String updateUrl
    ){
        client = new OkHttpClient();
        this.tokenManager = tokenManager;
        TOKEN_SERVER_URL = serverHost + ":" + serverPort;
        REGISTRATION_URL = registrationUrl;
        UPDATE_URL = updateUrl;

    }

    @Override
    public void deleteUserOnAuthorizationServer(Long id){
        try{
            Request request = new Request.Builder()
                    .url(TOKEN_SERVER_URL + UPDATE_URL + "/" + id)
                    .addHeader("JWT", tokenManager.getAccessToken())
                    .delete()
                    .build();
            Response response = client.newCall(request).execute();
            if (response.code() != 200){
                throw new JwtUpdateException();
            }
        } catch (Exception ex){
            throw new JwtUpdateException(ex);
        }
    }

    @Override
    public void updateUserOnAuthorizationServer(JwtUpdateForm form){
        try{
            JSONObject body = new JSONObject()
                    .put("accountId", form.getAccountId())
                    .put("login", form.getLogin())
                    .put("mail", form.getMail())
                    .put("hashPassword", form.getHashPassword())
                    .put("state", form.getState())
                    .put("role", form.getRole());
            RequestBody requestBody = RequestBody.create(JSON, body.toString());
            Request request = new Request.Builder()
                    .url(TOKEN_SERVER_URL + UPDATE_URL)
                    .addHeader("JWT", tokenManager.getAccessToken())
                    .patch(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.code() != 200){
                throw new JwtUpdateException();
            }
        } catch (Exception ex){
            throw new JwtUpdateException(ex);
        }

    }

    @Override
    public void registerOnAuthorizationServer(TokenRegistrationForm form){
        try{
            JSONObject body = new JSONObject()
                    .put("accountId", form.getAccountId())
                    .put("login", form.getLogin())
                    .put("mail", form.getMail())
                    .put("hashPassword", form.getHashPassword())
                    .put("state", form.getState())
                    .put("role", form.getRole());
            RequestBody requestBody = RequestBody.create(JSON, body.toString());
            Request request = new Request.Builder()
                    .url(TOKEN_SERVER_URL + REGISTRATION_URL)
                    .addHeader("JWT", tokenManager.getAccessToken())
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.code() != 200){
                throw new JwtRegistrationException();
            }
        } catch (Exception ex) {
            throw new JwtRegistrationException(ex);
        }
    }

}
