package ch.hearc.boardel.repository;

import ch.hearc.boardel.model.CustomUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomUserRepository extends CrudRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);
    CustomUser findByEmail(String email);

    List<CustomUser> findByUsernameContaining(String username);
}
