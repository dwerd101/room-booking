package ru.metrovagonmash.config.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.repository.ProfileRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final ProfileRepository profileRepository;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByLogin(login).orElseThrow(
                () -> new UsernameNotFoundException("User doesn't exist")
        );
        return AuthUser.fromUser(profile);
    }
}
