package ch.hearc.boardel.repository;

import ch.hearc.boardel.model.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long> {
}
