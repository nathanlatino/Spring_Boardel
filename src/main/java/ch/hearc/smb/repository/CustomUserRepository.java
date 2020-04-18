package ch.hearc.smb.repository;

import ch.hearc.smb.model.CustomUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomUserRepository extends CrudRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);
    CustomUser findByEmail(String email);

    List<CustomUser> findByUsernameContaining(String username);
}
