package cat.itacademy.s05.t02.n01.S05T02N01.service;

import cat.itacademy.s05.t02.n01.S05T02N01.model.Person;
import cat.itacademy.s05.t02.n01.S05T02N01.model.PersonPrincipal;
import cat.itacademy.s05.t02.n01.S05T02N01.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
/*
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
*/
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

//@Service
public class MyUserDetailsService {
//implements UserDetailsService
/*
    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Mono<Person> userMono = personRepository.findByUserName(userName);
        Person user = userMono.block();
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }
        return new PersonPrincipal(user);
    }
*/
}
