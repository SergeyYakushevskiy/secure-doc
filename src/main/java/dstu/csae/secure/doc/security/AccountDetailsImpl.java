package dstu.csae.secure.doc.security;

import dstu.csae.secure.doc.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class AccountDetailsImpl implements UserDetails {

    private Integer id;
    private String login;
    private String password;
    private String mail;
    private String phone;

    public static AccountDetailsImpl build(Account account){
        return new AccountDetailsImpl(
                account.getUserId(),
                account.getLogin(),
                account.getPasswordHash(),
                account.getEmail(),
                account.getPhone()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
