package cat.itacademy.s05.t02.n01.S05T02N01.service;

import cat.itacademy.s05.t02.n01.S05T02N01.model.Person;
import cat.itacademy.s05.t02.n01.S05T02N01.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PersonService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private PersonRepository personRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Mono<Person> verify(String userName, String userPassword){
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, userPassword));
        return personRepository.findByUserName(userName)
                .flatMap(user -> {
                    if (authentication.isAuthenticated()){
                        String token = jwtService.generateToken(userName);
                        return Mono.just(user);
                    } else {
                        return Mono.empty();
                    }
                });
    }

    public Mono<Person> createUser(String name, String password, String role) {
        Person user = new Person();
        userInfo(user, name, password, role);
        return personRepository.save(user);
    }

    public void userInfo(Person user, String name, String password, String role) {
        user.setUserName(name);
        user.setUserPassword(encoder.encode(password));
        user.setUserRole(role);
        user.setPetList(new ArrayList<>());
    }

    public Mono<Person> getUserPets(String userId) {
        return personRepository.findById(userId);
    }

    public Mono<Void> deleteUser(String userId){
        return personRepository.deleteById(userId);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUserName(username)
//                .map(user -> new org.springframework.security.core.userdetails.
//                        User(user.getUserName(), user.getUserPassword(), new ArrayList<>()))
//                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found with username: " + username)))
//                .block();
//    }

}
