package dstu.csae.secure.doc.service;

import dstu.csae.secure.doc.model.Account;
import dstu.csae.secure.doc.repository.AccountRepository;
import dstu.csae.secure.doc.security.AccountDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private AccountRepository accountRepository;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        Account account = accountRepository.findByLogin(login).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format("Пользователь с именем %s не найден", login))
        );
        logger.info(account.toString());
        return AccountDetailsImpl.build(account);
    }
}
