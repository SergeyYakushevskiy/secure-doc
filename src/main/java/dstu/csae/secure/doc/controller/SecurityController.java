package dstu.csae.secure.doc.controller;

import dstu.csae.secure.doc.dto.SigninRequest;
import dstu.csae.secure.doc.dto.SignupRequest;
import dstu.csae.secure.doc.model.Account;
import dstu.csae.secure.doc.repository.AccountRepository;
import dstu.csae.secure.doc.security.JwtCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtCore jwtCore;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setJwtCore(JwtCore jwtCore){
        this.jwtCore = jwtCore;
    }

    @PostMapping("/signin")
    ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest){
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signinRequest.getLogin(),
                            signinRequest.getPassword())
            );
        }catch (BadCredentialsException ex){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){
        if(accountRepository.existsByLogin(signupRequest.getLogin())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Выберите другое имя пользователя");
        }
        if(accountRepository.existsByPhone(signupRequest.getPhone())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким номером телефона уже зарегистрирован");
        }
        if(accountRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с такой почтой уже зарегистрирован");
        }
        String hashed = passwordEncoder.encode(signupRequest.getPassword());
        Account account = new Account();
        account.setLogin(signupRequest.getLogin());
        account.setPasswordHash(hashed);
        account.setEmail(signupRequest.getEmail());
        account.setPhone(signupRequest.getPhone());
        account.setCreatedAt(LocalDateTime.now());
        accountRepository.save(account);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }



}
