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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

@Repository
@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = BoardelApplication.class)
public class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testRole() {

        CustomUser user = new CustomUser();
        user.setUsername("test");
        user.setPassword("12345678");
        user.setEmail("test@test.com");

        entityManager.persist(user);
        entityManager.flush();

        Role role = new Role();
        role.setName("test_role");
        Set<CustomUser> userSet  = new HashSet<>();
        userSet.add(user);
        role.setCustomUsers(userSet);

        entityManager.persist(role);
        entityManager.flush();

        Optional<Role> roleRecherche = roleRepository.findById(role.getId());

        assertTrue(roleRecherche.isPresent());
        assertTrue(roleRecherche.get().getId().equals(role.getId()));
        assertTrue(roleRecherche.get().getName().equals(role.getName()));
        assertTrue(roleRecherche.get().getCustomUsers().equals(role.getCustomUsers()));

    }

}
