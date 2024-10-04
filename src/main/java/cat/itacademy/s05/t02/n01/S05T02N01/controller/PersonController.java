package cat.itacademy.s05.t02.n01.S05T02N01.controller;

import cat.itacademy.s05.t02.n01.S05T02N01.model.Person;
import cat.itacademy.s05.t02.n01.S05T02N01.model.Pet;
import cat.itacademy.s05.t02.n01.S05T02N01.service.PersonService;
import cat.itacademy.s05.t02.n01.S05T02N01.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PetService petService;

    @PostMapping("/login")
    public Mono<ResponseEntity<Person>> login(@RequestBody Map<String, String> info) {
        String userName = info.get("userName");
        String userPassword = info.get("userPassword");

        return personService.verify(userName, userPassword)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(401).build());
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<Person>> createUser(@RequestBody Map<String, String> info) {
        String userName = info.get("userName");
        String userPassword = info.get("userPassword");
        String userRole = info.get("userRole");
        return personService.createUser(userName, userPassword, userRole)
                .map(user ->ResponseEntity.status(201).body(user));
    }

    @GetMapping("/get/{userId}")
    public Mono<ResponseEntity<Person>> getUserPets(@PathVariable String userId) {
        return personService.getUserPets(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Flux<ResponseEntity<Pet>> getAllPets(){
        return personService.getAllPets()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String userId) {
        return personService.deleteUser(userId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
