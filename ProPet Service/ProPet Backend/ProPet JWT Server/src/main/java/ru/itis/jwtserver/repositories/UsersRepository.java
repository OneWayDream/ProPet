package ru.itis.jwtserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.jwtserver.models.DataAccessUser;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<DataAccessUser, Long> {

    Optional<DataAccessUser> findByLogin(String login);

}
