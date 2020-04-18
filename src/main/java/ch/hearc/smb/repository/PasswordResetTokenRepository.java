package ch.hearc.smb.repository;

import ch.hearc.smb.model.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository  extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

}
