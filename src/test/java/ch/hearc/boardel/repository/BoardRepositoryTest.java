package ch.hearc.boardel.repository;

import ch.hearc.boardel.BoardelApplication;
import ch.hearc.boardel.model.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = BoardelApplication.class)
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
    }
}
