package ch.hearc.boardel.repository;


import ch.hearc.boardel.BoardelApplication;
import ch.hearc.boardel.model.CustomUser;
import ch.hearc.boardel.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

@Repository
@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = BoardelApplication.class)
public class CustomUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomUserRepository customUserRepository;

    @Test
    public void testCustomUser() {

        Role role = new Role();
        role.setName("test_role");

        entityManager.persist(role);
        entityManager.flush();

        CustomUser user = new CustomUser();
        user.setUsername("test");
        user.setPassword("12345678");
        user.setEmail("test@test.com");
        user.setRole(role);

        entityManager.persist(user);
        entityManager.flush();

        Optional<CustomUser> customUserRecherche = customUserRepository.findById(user.getId());

        assertTrue(customUserRecherche.isPresent());
        assertTrue(customUserRecherche.get().getId().equals(user.getId()));
        assertTrue(customUserRecherche.get().getUsername().equals(user.getUsername()));
        assertTrue(customUserRecherche.get().getPassword().equals(user.getPassword()));
        assertTrue(customUserRecherche.get().getEmail().equals(user.getEmail()));
        assertTrue(customUserRecherche.get().getRoles().equals(user.getRoles()));

    }
}
