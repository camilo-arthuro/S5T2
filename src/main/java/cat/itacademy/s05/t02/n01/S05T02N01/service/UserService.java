package cat.itacademy.s05.t02.n01.S05T02N01.service;

import cat.itacademy.s05.t02.n01.S05T02N01.model.Pet;
import cat.itacademy.s05.t02.n01.S05T02N01.model.User;
import cat.itacademy.s05.t02.n01.S05T02N01.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public Mono<User> createUser(String name, String password, String role) {
        User user = new User();
        userInfo(user, name, password, role);
        return userRepository.save(user);
    }

    public void userInfo(User user, String name, String password, String role) {
        user.setUserName(name);
        user.setUserPassword(password);
        user.setUserRole(role);
        user.setPetList(new ArrayList<>());
    }


    public Mono<User> getUserPets(String userId) {
        return userRepository.findById(userId);
    }

    public Mono<Void> deleteUser(String userId){
        return userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
                .map(user -> new org.springframework.security.core.userdetails.
                        User(user.getUserName(), user.getUserPassword(), new ArrayList<>()))
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found with username: " + username)))
                .block();
    }

    public Mono<User> authenticate(String username, String password) {
        return userRepository.findByUserName(username)
                .flatMap(user -> {
                    if (passwordEncoder.matches(password, user.getUserPassword())) {
                        return Mono.just(user);
                    } else {
                        return Mono.empty();
                    }
                });
    }


}
