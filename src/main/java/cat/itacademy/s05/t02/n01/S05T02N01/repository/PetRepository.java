package cat.itacademy.s05.t02.n01.S05T02N01.repository;

import cat.itacademy.s05.t02.n01.S05T02N01.model.Pet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends ReactiveMongoRepository<Pet, String> {
}
