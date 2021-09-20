
package ru.metrovagonmash.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.PasswordConfirmationToken;
import ru.metrovagonmash.model.Profile;

import java.util.Optional;

@Repository
public interface PasswordConfirmationTokenRepository extends JpaRepository<PasswordConfirmationToken,Long> {

    Optional<PasswordConfirmationToken> findByToken(String token);


}

