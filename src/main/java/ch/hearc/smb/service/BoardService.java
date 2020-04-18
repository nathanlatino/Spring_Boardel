package ch.hearc.smb.service;

import ch.hearc.smb.model.Board;
import ch.hearc.smb.model.Post;
import ch.hearc.smb.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Component
public class BoardService {
    @Autowired
    private BoardRepository brepo;

    public List<Board> findAll() {
        List<Board> boards = new ArrayList<>();
        brepo.findAll().forEach(boards::add);
        return boards;
    }

    public Board find(Long id) {
        return brepo.findById(id).orElse(new Board());
    }

    public Board save(@Valid Board board) {
        return brepo.save(board);
    }

    public void delete(@Valid Board board) {
        brepo.delete(board);
    }

    public void delete(Long id) {
        this.delete(this.find(id));
    }

    public List<Post> getPosts(@Valid Board board) {
        return board.getPosts();
    }

}
