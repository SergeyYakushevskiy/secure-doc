package dstu.csae.secure.doc.repository;

import dstu.csae.secure.doc.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByLogin(String login);
    Optional<Account> findByPhone(String phone);
    Optional<Account> findByEmail(String email);

    boolean existsByLogin(String login);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
}
