package cat.itacademy.s05.t02.n01.S05T02N01.service;

import cat.itacademy.s05.t02.n01.S05T02N01.model.Person;
import cat.itacademy.s05.t02.n01.S05T02N01.model.Pet;
import cat.itacademy.s05.t02.n01.S05T02N01.repository.PetRepository;
import cat.itacademy.s05.t02.n01.S05T02N01.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PersonRepository personRepository;

    public Mono<Pet> createPet(String userId, String petName, String petColor, String petBreed) {
        Pet pet = new Pet();
        petInfo(userId, pet, petName, petColor, petBreed);
        return petRepository.save(pet)
                .flatMap(newPet -> personRepository.findById(userId)
                        .flatMap(user -> {
                            user.getPetList().add(newPet);
                            return personRepository.save(user);
                        })
                        .thenReturn(newPet));
    }

    public void petInfo(String userId, Pet pet, String petName, String petColor, String petBreed){
        pet.setName(petName);
        pet.setColor(petColor);
        pet.setBreed(petBreed);
        pet.setHappiness(100);
        pet.setHealth(100);
        pet.setOwnerId(userId);
    }

    public Mono<Pet> updatePet(String userId, String petId, String update, String change){
        return petRepository.findById(petId)
                .flatMap(pet -> {
                    update(pet, update, change);
                    return petRepository.save(pet)
                            .then(updatePetUser(userId, petId, update, change))
                            .thenReturn(pet);
                });
    }

    public void update(Pet pet, String update, String change){
        switch (update) {
            case "change_name":
                pet.setName(change);
                break;
            case "change_color":
                pet.setColor(change);
                break;
            case "change_breed":
                pet.setBreed(change);
                break;
        }
    }

    public Mono<Person> updatePetUser(String userId, String petId, String update, String change){
        return personRepository.findById(userId)
                .flatMap(user -> {
                    user.getPetList().stream()
                            .filter(pet -> pet.getId().equals(petId))
                            .forEach(pet -> {
                                switch (update) {
                                    case "change_name":
                                        pet.setName(change);
                                        break;
                                    case "change_color":
                                        pet.setColor(change);
                                        break;
                                    case "change_breed":
                                        pet.setBreed(change);
                                        break;
                                }
                            });
                    return personRepository.save(user);
                });
    }

    public Mono<Pet> petAction(String userId, String petId, String action){
        return petRepository.findById(petId)
                .flatMap(pet -> {
                    action(pet, action);
                    return petRepository.save(pet)
                            .then(actionPetUser(userId, petId, action))
                            .thenReturn(pet);
                });
    }

    public void action(Pet pet, String action){
        switch (action) {
            case "play" -> {
                pet.setHappiness(pet.getHappiness() + 10);
                pet.setHealth(pet.getHealth() - 20);
            }
            case "feed" -> {
                pet.setHappiness(pet.getHappiness() + 10);
                pet.setHealth(pet.getHealth() + 10);
            }
            case "train" -> {
                pet.setHappiness(pet.getHappiness() - 20);
                pet.setHealth(pet.getHealth() + 10);
            }
        }
    }

    public Mono<Person> actionPetUser(String userId, String petId, String action){
        return personRepository.findById(userId)
                .flatMap(user -> {
                    user.getPetList().stream()
                            .filter(pet -> pet.getId().equals(petId))
                            .forEach(pet -> {
                                switch (action) {
                                    case "play" -> {
                                        pet.setHappiness(pet.getHappiness() + 10);
                                        pet.setHealth(pet.getHealth() - 20);
                                    }
                                    case "feed" -> {
                                        pet.setHappiness(pet.getHappiness() + 10);
                                        pet.setHealth(pet.getHealth() + 10);
                                    }
                                    case "train" -> {
                                        pet.setHappiness(pet.getHappiness() - 20);
                                        pet.setHealth(pet.getHealth() + 10);
                                    }
                                }
                            });
                    return personRepository.save(user);
                });
    }

    public Mono<Void> deletePet(String petId){
        return petRepository.findById(petId)
                .flatMap(pet -> petRepository.deleteById(petId)
                        .then(personRepository.findAll()
                                .flatMap(user -> {
                                    if (user.getPetList().removeIf(p -> p.getId().equals(petId))) {
                                        return personRepository.save(user);
                                    }
                                    return Mono.empty();
                                })
                                .then()));
    }

}
