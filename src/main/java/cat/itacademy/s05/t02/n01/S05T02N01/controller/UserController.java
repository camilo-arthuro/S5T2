package cat.itacademy.s05.t02.n01.S05T02N01.controller;

import cat.itacademy.s05.t02.n01.S05T02N01.model.User;
import cat.itacademy.s05.t02.n01.S05T02N01.service.UserService;
import cat.itacademy.s05.t02.n01.S05T02N01.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.ArrayList;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody Map<String, String> info) {
        String username = info.get("username");
        String password = info.get("password");

        return userService.authenticate(username, password)
                .flatMap(user -> {
                    String token = jwtTokenProvider.generateToken(
                            new org.springframework.security.core.userdetails.User(user.getUserName(),
                                    user.getUserPassword(), new ArrayList<>()));
                    Map<String, String> response = Map.of("token", token);
                    return Mono.just(ResponseEntity.ok(response));
                })
                .defaultIfEmpty(ResponseEntity.status(401).build());
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<User>> createUser(@RequestBody Map<String, String> info) {
        String name = info.get("name");
        String password = info.get("password");
        String role = info.get("role");
        return userService.createUser(name, password, role)
                .map(user ->ResponseEntity.status(201).body(user));
    }

    @GetMapping("/get/{userId}")
    public Mono<ResponseEntity<User>> getUserPets(@PathVariable String userId) {
        return userService.getUserPets(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
