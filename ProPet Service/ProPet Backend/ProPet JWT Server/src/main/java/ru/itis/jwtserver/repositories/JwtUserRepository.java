package ru.itis.jwtserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.jwtserver.models.JwtUser;

import java.util.Optional;

public interface JwtUserRepository extends JpaRepository<JwtUser, Long> {

    Optional<JwtUser> findByLogin(String login);
    Optional<JwtUser> findByMail(String mail);

}
