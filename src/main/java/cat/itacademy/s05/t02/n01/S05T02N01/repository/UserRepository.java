package cat.itacademy.s05.t02.n01.S05T02N01.repository;

import cat.itacademy.s05.t02.n01.S05T02N01.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUserName(String userName);
}
