package ru.itis.backend.services;

import ru.itis.backend.dto.app.AccountDto;
import ru.itis.backend.dto.rest.AccountRestDto;

import java.util.List;

public interface AccountService extends CrudService<AccountDto, Long> {

    List<AccountRestDto> findAllRest();
    AccountRestDto updateRest(AccountRestDto account);
    void deleteRest(AccountRestDto account);
    AccountRestDto addRest(AccountRestDto account);
    AccountRestDto findRestById(Long id);

    AccountDto activateUser(String linkValue);
    void banAccount(Long id);
    AccountDto findByLogin(String login);
    AccountRestDto findRestByLogin(String login);
    AccountDto findByMail(String mail);
    AccountRestDto findRestByMail(String mail);

}
