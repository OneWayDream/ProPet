package ru.itis.backend.services;

import ru.itis.backend.dto.AccountDto;

public interface AccountService extends CrudService<AccountDto, Long> {

    AccountDto activateUser(String linkValue);
    void banUser(Long userId);
    AccountDto findUserByLogin(String login);
    AccountDto findUserByMail(String mail);

}
