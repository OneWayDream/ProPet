package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.backend.models.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
    Optional<User> findByMail(String mail);

}
