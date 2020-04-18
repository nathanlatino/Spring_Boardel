package ch.hearc.smb.repository;

import ch.hearc.smb.model.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long> {
}
