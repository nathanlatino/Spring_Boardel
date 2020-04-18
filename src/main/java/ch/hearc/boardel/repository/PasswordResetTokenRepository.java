package ch.hearc.boardel.repository;

import ch.hearc.boardel.model.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository  extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

}
