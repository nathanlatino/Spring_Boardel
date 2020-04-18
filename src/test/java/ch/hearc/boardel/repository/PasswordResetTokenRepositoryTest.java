package ch.hearc.boardel.repository;


import ch.hearc.boardel.model.CustomUser;
import ch.hearc.boardel.model.PasswordResetToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class PasswordResetTokenRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Test
    public void testPasswordResetToken() {

        CustomUser user = new CustomUser();
        user.setUsername("test");
        user.setPassword("12345678");
        user.setEmail("test@test.com");

        entityManager.persist(user);
        entityManager.flush();

        PasswordResetToken passwordResetToken = new PasswordResetToken("12345");
        passwordResetToken.setUser(user);


        entityManager.persist(passwordResetToken);
        entityManager.flush();

        Optional<PasswordResetToken> roleRecherche = passwordResetTokenRepository.findById(passwordResetToken.getId());

        assertTrue(roleRecherche.isPresent());
        assertTrue(roleRecherche.get().getId().equals(passwordResetToken.getId()));
        assertTrue(roleRecherche.get().getToken().equals(passwordResetToken.getToken()));
        assertTrue(roleRecherche.get().getExpiryDate().equals(passwordResetToken.getExpiryDate()));
        assertTrue(roleRecherche.get().getUser().equals(passwordResetToken.getUser()));
        assertThat(roleRecherche.get()).isNotNull();

    }
}
