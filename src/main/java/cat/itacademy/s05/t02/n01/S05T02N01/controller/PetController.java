package cat.itacademy.s05.t02.n01.S05T02N01.controller;

import cat.itacademy.s05.t02.n01.S05T02N01.model.Pet;
import cat.itacademy.s05.t02.n01.S05T02N01.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping("/create/{userId}")
    public Mono<ResponseEntity<Pet>> createPet(@PathVariable String userId, @RequestBody Map<String, String> info) {
        String petName = info.get("petName");
        String petColor = info.get("petColor");
        String petBreed = info.get("petBreed");
        return petService.createPet(userId, petName, petColor, petBreed)
                .map(user -> ResponseEntity.status(201).body(user));
    }

    @PutMapping("/delete/{userId}")
    public Mono<ResponseEntity<Void>> deletePet(@PathVariable String userId, @RequestBody Map<String, String> info){
        String petId = info.get("petId");
        String petName = info.get("petName");
        return petService.deletePet(userId, petId, petName)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("update/{userId}")
    public Mono<ResponseEntity<Pet>> updatePet(@PathVariable String userId, @RequestBody Map<String, String> info){
        String petId = info.get("petId");
        String petName = info.get("petName");
        String update = info.get("update");
        String change = info.get("change");
        return petService.updatePet(userId, petId, petName, update, change)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("action/{userId}")
    public Mono<ResponseEntity<Pet>> petAction(@PathVariable String userId, @RequestBody Map<String, String> info){
        String petId = info.get("petId");
        String petName = info.get("petName");
        String action = info.get("action");
        return petService.petAction(userId, petId, petName, action)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
