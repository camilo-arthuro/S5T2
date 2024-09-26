package cat.itacademy.s05.t02.n01.S05T02N01.controller;

import cat.itacademy.s05.t02.n01.S05T02N01.model.Pet;
import cat.itacademy.s05.t02.n01.S05T02N01.model.User;
import cat.itacademy.s05.t02.n01.S05T02N01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create/user")
    public Mono<ResponseEntity<User>> createUser(@RequestBody Map<String, String> info) {
        String name = info.get("name");
        String password = info.get("password");
        String role = info.get("role");
        return userService.createUser(name, password, role)
                .map(user ->ResponseEntity.status(201).body(user));
    }

    @PostMapping("/create/pet")
    public Mono<ResponseEntity<User>> createPet(@RequestBody Pet pet) {
        return userService.createPet(pet);
    }

    @GetMapping("/read")
    public Flux<Pet> getAllPets() {
        return userService.getUserPets();
    }

    @GetMapping("/read/{id}")
    public Mono<Pet> getPetById(@PathVariable String id) {
        return userService.getPetById(id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deletePet(@PathVariable String id) {
        return userService.deletePet(id);
    }
}
