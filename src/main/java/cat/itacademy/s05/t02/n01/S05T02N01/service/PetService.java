package cat.itacademy.s05.t02.n01.S05T02N01.service;

import cat.itacademy.s05.t02.n01.S05T02N01.model.Pet;
import cat.itacademy.s05.t02.n01.S05T02N01.repository.PetRepository;
import cat.itacademy.s05.t02.n01.S05T02N01.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final PersonRepository userRepository;

    public Mono<Pet> createPet(String userId, String petName, String petColor, String petBreed) {
        Pet pet = new Pet();
        petInfo(pet, petName, petColor, petBreed);
        addPet(userId, pet);
        return petRepository.save(pet);
    }

    public void addPet(String userId, Pet pet){
        userRepository.findById(userId)
                .flatMap(user -> {
                    user.getPetList().add(pet);
                    return userRepository.save(user);
                });
    }

    public void petInfo(Pet pet, String petName, String petColor, String petBreed){
        pet.setName(petName);
        pet.setColor(petColor);
        pet.setBreed(petBreed);
        pet.setHappiness(100);
        pet.setHealth(100);
    }

    public Mono<Pet> updatePet(String userId, String petId, String petName, String update, String change){
        return petRepository.findById(petId)
                .flatMap(pet -> {
                    update(pet, update, change);
                    updatePetUser(userId, petName, update, change);

                    return petRepository.save(pet);
                });
    }

    public void update(Pet pet, String update, String change){
        if (update.equals("change_name")){
            pet.setName(change);
        } else if (update.equals("change_color")){
            pet.setColor(change);
        } else if (update.equals("change_breed")) {
            pet.setBreed(change);
        }
    }

    public void updatePetUser(String userId, String petName, String update, String change){
        userRepository.findById(userId)
                .flatMap(user -> {
                    int petPosition = findPet(user.getPetList(), petName);
                    if (user.getPetList().get(petPosition).getName().equals(petName)){
                        if (update.equals("change_name")){
                            user.getPetList().get(petPosition).setName(change);
                        } else if (update.equals("change_color")){
                            user.getPetList().get(petPosition).setColor(change);
                        } else if (update.equals("change_breed")) {
                            user.getPetList().get(petPosition).setBreed(change);
                        }
                    }

                    return userRepository.save(user);
                });
    }

    public Mono<Pet> petAction(String userId, String petId, String petName, String action){
        return petRepository.findById(petId)
                .flatMap(pet -> {
                    action(pet, action);
                    actionPetUser(userId, petName, action);

                    return petRepository.save(pet);
                });
    }

    public void action(Pet pet, String action){
        if (action.equals("play")){
            pet.setHappiness(pet.getHappiness()+10);
            pet.setHealth(pet.getHealth()-20);
        } else if (action.equals("feed")){
            pet.setHappiness(pet.getHappiness()+10);
            pet.setHealth(pet.getHealth()+10);
        } else if (action.equals("train")) {
            pet.setHappiness(pet.getHappiness()-20);
            pet.setHealth(pet.getHealth()+10);
        }
    }

    public void actionPetUser(String userId, String petName, String action){
        userRepository.findById(userId)
                .flatMap(user -> {
                    int petPosition = findPet(user.getPetList(), petName);
                    int petHappiness = user.getPetList().get(petPosition).getHappiness();
                    int petHealth = user.getPetList().get(petPosition).getHealth();

                    if (user.getPetList().get(petPosition).getName().equals(petName)){
                        if (action.equals("play")){
                            user.getPetList().get(petPosition).setHappiness(petHappiness + 10);
                            user.getPetList().get(petPosition).setHealth(petHealth - 20);
                        } else if (action.equals("feed")){
                            user.getPetList().get(petPosition).setHappiness(petHappiness + 10);
                            user.getPetList().get(petPosition).setHealth(petHealth + 10);
                        } else if (action.equals("train")) {
                            user.getPetList().get(petPosition).setHappiness(petHappiness - 20);
                            user.getPetList().get(petPosition).setHealth(petHealth + 10);
                        }
                    }
                    return userRepository.save(user);
                });
    }

    public int findPet(ArrayList<Pet> petList, String petName) {
        int petPosition = -1;

        for (int i = 0; i < petList.size(); i++) {
            if (petList.get(i).getName().equals(petName)){
                petPosition = i;
            }
        }
        return petPosition;
    }

    public Mono<Void> deletePet(String userId, String petId, String petName){
        removePet(userId, petName);
        return petRepository.deleteById(petId);
    }

    public void removePet(String userId, String petName){
        userRepository.findById(userId)
                .flatMap(user -> {
                    int petPosition = findPet(user.getPetList(), petName);
                    user.getPetList().remove(petPosition);

                    return userRepository.save(user);
                });
    }
}
