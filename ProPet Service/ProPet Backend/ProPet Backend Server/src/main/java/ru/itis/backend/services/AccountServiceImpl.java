package ru.itis.backend.services;

import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.ActivationLinkDto;
import ru.itis.backend.dto.AccountDto;
import ru.itis.backend.dto.TokenRegistrationForm;
import ru.itis.backend.exceptions.*;
import ru.itis.backend.models.Account;
import ru.itis.backend.models.ActivationLink;
import ru.itis.backend.models.UserState;
import ru.itis.backend.repositories.AccountRepository;
import ru.itis.backend.security.managers.TokenManager;
import ru.itis.backend.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    protected OkHttpClient client;

    protected final AccountRepository repository;
    protected final ActivationLinksService activationLinksService;
    protected final TokenManager tokenManager;

    protected final String TOKEN_SERVER_URL;
    protected final String REGISTRATION_URL;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Autowired
    public AccountServiceImpl(
            AccountRepository accountRepository,
            ActivationLinksService activationLinksService,
            TokenManager tokenManager,
            @Value("${security.jwt.token-server-host}") String serverHost,
            @Value("${security.jwt.token-server-port}") String serverPort,
            @Value("${security.jwt.registration-url}") String registrationUrl
    ){
        client = new OkHttpClient();
        this.repository = accountRepository;
        this.activationLinksService = activationLinksService;
        this.tokenManager = tokenManager;
        TOKEN_SERVER_URL = serverHost + ":" + serverPort;
        REGISTRATION_URL = registrationUrl;
    }

    @Override
    public List<AccountDto> findAll() {
        return AccountDto.from(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(AccountDto userDto) {
        try{
            Account entityToDelete = repository.findById(userDto.getId())
                    .filter(entry -> !entry.getIsDeleted())
                    .orElseThrow(EntityNotExistsException::new);
            entityToDelete.setIsDeleted(true);
            repository.save(entityToDelete);
        } catch (Exception ex){
            if (ex instanceof EntityNotExistsException){
                throw ex;
            }
            throw new PersistenceException(ex);
        }
    }

    @Override
    public AccountDto add(AccountDto userDto) {
        Account newEntity = AccountDto.to(userDto);
        repository.save(newEntity);
        return AccountDto.from(newEntity);
    }

    @Override
    public AccountDto findById(Long aLong) {
        return AccountDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        try{
            AccountDto entity = findById(accountDto.getId());
            PropertiesUtils.copyNonNullProperties(accountDto, entity);
            Account updatedEntity = repository.save(AccountDto.to(entity));
            return AccountDto.from(updatedEntity);
        } catch (Exception ex){
            try{
                String message = ex.getCause().getCause().getMessage();
                if (message.contains("account_mail_key")){
                    throw new MailAlreadyInUseException(ex);
                } else if (message.contains("account_login_key")){
                    throw new LoginAlreadyInUseException(ex);
                }
            } catch (NullPointerException exception){
                //ignore
            }
            throw ex;
        }
    }

    @Override
    public AccountDto activateUser(String linkValue) {
        ActivationLinkDto activationLinkDto = activationLinksService.findByLinkValue(linkValue);
        Account userForActivation = repository.getById(activationLinkDto.getAccountId());
        userForActivation.setState(UserState.ACTIVE);
        repository.save(userForActivation);
        activationLinksService.delete(activationLinkDto);

        registerOnAuthorizationServer(TokenRegistrationForm.builder()
                .login(userForActivation.getLogin())
                .mail(userForActivation.getMail())
                .hashPassword(userForActivation.getHashPassword())
                .state(userForActivation.getState())
                .role(userForActivation.getRole())
                .build());
        return AccountDto.from(userForActivation);
    }

    protected void registerOnAuthorizationServer(TokenRegistrationForm form){
        try{
            JSONObject body = new JSONObject()
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

    @Override
    public void banUser(Long userId) {
        try{
            Account userForBan = repository.findById(userId)
                    .filter(entry -> entry.getIsDeleted()==null)
                    .orElseThrow(EntityNotExistsException::new);
            userForBan.setState(UserState.BANNED);
            repository.save(userForBan);
        } catch (Exception ex){
            throw new PersistenceException(ex);
        }
    }

    @Override
    public AccountDto findUserByLogin(String login) {
        return AccountDto.from(repository.findByLogin(login)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public AccountDto findUserByMail(String mail) {
        return AccountDto.from(repository.findByMail(mail)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }
}
