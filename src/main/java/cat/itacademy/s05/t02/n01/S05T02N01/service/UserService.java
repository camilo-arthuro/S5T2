package cat.itacademy.s05.t02.n01.S05T02N01.service;

import cat.itacademy.s05.t02.n01.S05T02N01.model.Pet;
import cat.itacademy.s05.t02.n01.S05T02N01.model.User;
import cat.itacademy.s05.t02.n01.S05T02N01.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

    public Mono<User> createPet(String userId, String petName, String petColor) {
        Pet pet = new Pet();
        petInfo(pet, petName, petColor);
        return userRepository.findById(userId)
                .flatMap(user -> {
                    user.getPetList().add(pet);
                    return userRepository.save(user);
                });
    }

    public void petInfo(Pet pet, String petName, String petColor){
        pet.setName(petName);
        pet.setColor(petColor);
        pet.setHappiness(100);
        pet.setHealth(100);
    }

    public Mono<User> getUserPets(String userId) {
        return userRepository.findById(userId);
    }

    public Mono<User> updatePet(String userId, String petName, String petColor, int petHappiness, int health) {
        return userRepository.findById(userId)
                .flatMap(user -> {
                    int petPosition = findPet(user.getPetList(), petName);
                    Pet pet = user.getPetList().get(petPosition);
                    pet.setColor(petColor);
                    pet.setHappiness(petHappiness);
                    pet.setHealth(health);
                    user.getPetList().set(petPosition, pet);
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

    public Mono<User> deletePet(String userId, String petName) {
        return userRepository.findById(userId)
                .flatMap(user -> {
                    int petPosition = findPet(user.getPetList(), petName);
                    user.getPetList().remove(petPosition);

                    return userRepository.save(user);
                });
    }

}
