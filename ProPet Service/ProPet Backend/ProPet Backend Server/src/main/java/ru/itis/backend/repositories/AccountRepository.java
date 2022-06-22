package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.backend.models.Account;

import java.util.Optional;

public interface AccountRepository extends CustomRepository<Account, Long> {

    Optional<Account> findByLogin(String login);
    Optional<Account> findByMail(String mail);

}
