package cat.itacademy.s05.t02.n01.S05T02N01.repository;

import cat.itacademy.s05.t02.n01.S05T02N01.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, String> {
    Mono<Person> findByUserName(String userName);
}
