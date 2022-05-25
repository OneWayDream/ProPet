package ru.itis.backend.services;

import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.app.AccountDto;
import ru.itis.backend.dto.forms.JwtUpdateForm;
import ru.itis.backend.dto.rest.ActivationLinkDto;
import ru.itis.backend.dto.rest.AccountRestDto;
import ru.itis.backend.dto.forms.TokenRegistrationForm;
import ru.itis.backend.exceptions.jwtserver.JwtRegistrationException;
import ru.itis.backend.exceptions.jwtserver.JwtUpdateException;
import ru.itis.backend.exceptions.persistence.EntityNotExistsException;
import ru.itis.backend.exceptions.persistence.EntityNotFoundException;
import ru.itis.backend.exceptions.registration.LoginAlreadyInUseException;
import ru.itis.backend.exceptions.registration.MailAlreadyInUseException;
import ru.itis.backend.models.Account;
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
    protected final ActivationLinkService activationLinksService;
    protected final TokenManager tokenManager;

    protected final String TOKEN_SERVER_URL;
    protected final String REGISTRATION_URL;
    protected final String UPDATE_URL;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Autowired
    public AccountServiceImpl(
            AccountRepository accountRepository,
            ActivationLinkService activationLinksService,
            TokenManager tokenManager,
            @Value("${security.jwt.token-server-host}") String serverHost,
            @Value("${security.jwt.token-server-port}") String serverPort,
            @Value("${security.jwt.registration-url}") String registrationUrl,
            @Value("${security.jwt.update-url}") String updateUrl
    ){
        client = new OkHttpClient();
        this.repository = accountRepository;
        this.activationLinksService = activationLinksService;
        this.tokenManager = tokenManager;
        TOKEN_SERVER_URL = serverHost + ":" + serverPort;
        REGISTRATION_URL = registrationUrl;
        UPDATE_URL = updateUrl;
    }

    @Override
    public List<AccountDto> findAll() {
        return AccountDto.from(findAllAccounts());
    }

    @Override
    public List<AccountRestDto> findAllRest() {
        return AccountRestDto.fromRest(findAllAccounts());
    }

    protected List<Account> findAllAccounts() {
        return repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRest(AccountRestDto account) {
        deleteAccount(AccountRestDto.toRest(account));
    }

    @Override
    public void delete(AccountDto account) {
        deleteAccount(AccountDto.to(account));
    }

    protected void deleteAccount(Account account) {
        try{
            Account entityToDelete = repository.findById(account.getId())
                    .filter(entry -> !entry.getIsDeleted())
                    .orElseThrow(EntityNotExistsException::new);
            entityToDelete.setIsDeleted(true);
            entityToDelete.getActivationLink().setIsDeleted(true);
            entityToDelete.getSitterInfo().setIsDeleted(true);
            entityToDelete.getPets().forEach(pet -> pet.setIsDeleted(true));
            entityToDelete.getAppeals().forEach(appeal -> appeal.setIsDeleted(true));
            repository.save(entityToDelete);
            deleteUserOnAuthorizationServer(entityToDelete.getId());
        } catch (Exception ex){
            if (ex instanceof EntityNotExistsException){
                throw ex;
            }
            throw new PersistenceException(ex);
        }
    }

    protected void deleteUserOnAuthorizationServer(Long id){
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
    public AccountDto add(AccountDto account) {
        return AccountDto.from(addAccount(AccountDto.to(account)));
    }

    @Override
    public AccountRestDto addRest(AccountRestDto account) {
        return AccountRestDto.fromRest(addAccount(AccountRestDto.toRest(account)));
    }

    protected Account addAccount(Account account) {
        return repository.save(account);
    }

    @Override
    public AccountDto findById(Long aLong) {
        return AccountDto.from(findAccountById(aLong));
    }

    @Override
    public AccountRestDto findRestById(Long aLong) {
        return AccountRestDto.fromRest(findAccountById(aLong));
    }

    protected Account findAccountById(Long aLong) {
        return repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public AccountDto update(AccountDto account){
        return AccountDto.from(updateAccount(AccountDto.to(account)));
    }

    @Override
    public AccountRestDto updateRest(AccountRestDto account){
        return AccountRestDto.fromRest(updateAccount(AccountRestDto.toRest(account)));
    }

    protected Account updateAccount(Account updatedAccount) {
        try{
            Account entity = repository.findById(updatedAccount.getId())
                    .filter(entry -> !entry.getIsDeleted())
                    .orElseThrow(EntityNotFoundException::new);
            if (updatedAccount.getSitterInfo() != null){
                PropertiesUtils.copyNonNullProperties(updatedAccount.getSitterInfo(), entity.getSitterInfo());
                updatedAccount.setSitterInfo(null);
            }
            PropertiesUtils.copyNonNullProperties(updatedAccount, entity);
            entity = repository.save(entity);
            updateUserOnAuthorizationServer(JwtUpdateForm.builder()
                    .accountId(entity.getId())
                    .login(entity.getLogin())
                    .mail(entity.getMail())
                    .hashPassword(entity.getHashPassword())
                    .role(entity.getRole())
                    .state(entity.getState())
                    .build());
            return repository.save(entity);
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

    protected void updateUserOnAuthorizationServer(JwtUpdateForm form){
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
    public AccountDto activateUser(String linkValue) {
        ActivationLinkDto activationLinkDto = activationLinksService.findByLinkValue(linkValue);
        Account userForActivation = repository.getById(activationLinkDto.getAccountId());
        userForActivation.setState(UserState.ACTIVE);
        repository.save(userForActivation);
        activationLinksService.delete(activationLinkDto);

        registerOnAuthorizationServer(TokenRegistrationForm.builder()
                .accountId(userForActivation.getId())
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

    @Override
    public void banAccount(Long id) {
        try{
            Account userForBan = repository.findById(id)
                    .filter(entry -> entry.getIsDeleted()==null)
                    .orElseThrow(EntityNotExistsException::new);
            userForBan.setState(UserState.BANNED);
            repository.save(userForBan);
        } catch (Exception ex){
            throw new PersistenceException(ex);
        }
    }

    @Override
    public AccountDto findByLogin(String login) {
        return AccountDto.from(findAccountByLogin(login));
    }

    @Override
    public AccountRestDto findRestByLogin(String login) {
        return AccountRestDto.fromRest(findAccountByLogin(login));
    }

    protected Account findAccountByLogin(String login) {
        return repository.findByLogin(login)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public AccountDto findByMail(String mail) {
        return AccountDto.from(findAccountByMail(mail));
    }

    @Override
    public AccountRestDto findRestByMail(String mail) {
        return AccountRestDto.fromRest(findAccountByMail(mail));
    }

    protected Account findAccountByMail(String mail) {
        return repository.findByMail(mail)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new);
    }
}
