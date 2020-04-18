package ch.hearc.smb.repository;

import ch.hearc.boardel.repository.BoardRepository;
import ch.hearc.boardel.model.Board;
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
public class BoardRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testBoard() {
        Board board = new Board();
        board.setName("test board");
        board.setDescription("test description");

        entityManager.persist(board);
        entityManager.flush();

        Optional<Board> boardRecherche = boardRepository.findById(board.getId());

        assertTrue(boardRecherche.isPresent());
        assertTrue(boardRecherche.get().getId().equals(board.getId()));
        assertTrue(boardRecherche.get().getName().equals(board.getName()));
        assertTrue(boardRecherche.get().getDescription().equals(board.getDescription()));
        assertThat(boardRecherche.get()).isNotNull();

    }
}
