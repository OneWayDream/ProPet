package ru.itis.backend.services;

import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    protected final AccountRepository repository;
    protected final ActivationLinkService activationLinksService;
    protected final JwtModuleService jwtModuleService;

    @Autowired
    public AccountServiceImpl(
            AccountRepository accountRepository,
            ActivationLinkService activationLinksService,
            JwtModuleServiceImpl jwtModuleService
    ){
        this.repository = accountRepository;
        this.activationLinksService = activationLinksService;
        this.jwtModuleService = jwtModuleService;
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
                .filter(Account::isPresent)
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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    protected void deleteAccount(Account account) {
        try{
            Account entityToDelete = repository.findById(account.getId())
                    .filter(entity -> !entity.getIsDeleted())
                    .orElseThrow(() -> new EntityNotExistsException("account."));
            entityToDelete.setIsDeleted(true);
            entityToDelete.getActivationLink().setIsDeleted(true);
            entityToDelete.getSitterInfo().setIsDeleted(true);
            entityToDelete.getPets().forEach(pet -> pet.setIsDeleted(true));
            entityToDelete.getAppeals().forEach(appeal -> appeal.setIsDeleted(true));
            entityToDelete.getTreatyInfo().setIsDeleted(true);
            repository.save(entityToDelete);
            jwtModuleService.deleteUserOnAuthorizationServer(entityToDelete.getId());
        } catch (Exception ex){
            if (ex instanceof EntityNotExistsException){
                throw ex;
            }
            throw new PersistenceException(ex);
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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    protected Account addAccount(Account account) {
        try{
            return repository.save(account);
        } catch (Exception ex){
            try {
                String message = ex.getCause().getCause().getMessage();
                if (message.contains("account_mail_key")) {
                    throw new MailAlreadyInUseException(ex);
                } else if (message.contains("account_login_key")) {
                    throw new LoginAlreadyInUseException(ex);
                }
            } catch (NullPointerException exception) {
                //ignore
            }
            throw ex;
        }
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
                .filter(Account::isPresent)
                .orElseThrow(() -> new EntityNotFoundException(" account."));
    }

    @Override
    public AccountDto update(AccountDto account){
        return AccountDto.from(updateAccount(AccountDto.to(account)));
    }

    @Override
    public AccountRestDto updateRest(AccountRestDto account){
        return AccountRestDto.fromRest(updateAccount(AccountRestDto.toRest(account)));
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    protected Account updateAccount(Account updatedAccount) {
        try{
            Account entity = repository.findById(updatedAccount.getId())
                    .filter(Account::isPresent)
                    .orElseThrow(() -> new EntityNotFoundException(" account."));
            if (updatedAccount.getSitterInfo() != null){
                PropertiesUtils.copyNonNullProperties(updatedAccount.getSitterInfo(), entity.getSitterInfo());
                updatedAccount.setSitterInfo(null);
            }
            PropertiesUtils.copyNonNullProperties(updatedAccount, entity);
            entity = repository.save(entity);
            jwtModuleService.updateUserOnAuthorizationServer(JwtUpdateForm.builder()
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public AccountDto activateUser(String linkValue) {
        ActivationLinkDto activationLinkDto = activationLinksService.findByLinkValue(linkValue);
        Account userForActivation = repository.getById(activationLinkDto.getAccountId());
        userForActivation.setState(UserState.ACTIVE);
        repository.save(userForActivation);
        activationLinksService.delete(activationLinkDto);

        jwtModuleService.registerOnAuthorizationServer(TokenRegistrationForm.builder()
                .accountId(userForActivation.getId())
                .login(userForActivation.getLogin())
                .mail(userForActivation.getMail())
                .hashPassword(userForActivation.getHashPassword())
                .state(userForActivation.getState())
                .role(userForActivation.getRole())
                .build());
        return AccountDto.from(userForActivation);
    }

    @Override
    public void banAccount(Long id) {
        try{
            Account userForBan = repository.findById(id)
                    .filter(Account::isPresent)
                    .orElseThrow(() -> new EntityNotExistsException("account."));
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
                .filter(Account::isPresent)
                .orElseThrow(() -> new EntityNotFoundException(" account."));
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
                .filter(Account::isPresent)
                .orElseThrow(() -> new EntityNotFoundException(" account."));
    }
}
