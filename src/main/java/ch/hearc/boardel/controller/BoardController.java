package ch.hearc.boardel.controller;


import ch.hearc.boardel.model.Board;
import ch.hearc.boardel.model.CustomUser;
import ch.hearc.boardel.model.Post;
import ch.hearc.boardel.service.BoardService;
import ch.hearc.boardel.service.CustomUserService;
import ch.hearc.boardel.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    BoardService boardService;

    @Autowired
    PostService postService;

    @Autowired
    CustomUserService customUserDetailsService;

    private static final String BOARD_PARAM_NAME = "board";
    private static final String BOARD_PARAM_FORM = "board_form";

    @GetMapping("/{id}")
    public String specificBoard(Model model, @PathVariable Long id, @RequestParam(defaultValue = "") String search , @PageableDefault(value=5, page=0) Pageable pageable) {
        Board board = boardService.find(id);
        Page<Post> posts = postService.findByName(board.getId(), search, pageable);
        List<String> dates = posts.stream().map(Post::getDateDisplay).collect(Collectors.toList());
        model.addAttribute("search", search);
        model.addAttribute(BOARD_PARAM_NAME, board);
        model.addAttribute("posts", posts);
        model.addAttribute("dates", dates);
        model.addAttribute("pageUrl", "/boards/" + board.getId() + "?search=" + search);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser actualUser = customUserDetailsService.findByCustomusername(authentication.getName());
        model.addAttribute("currentUser", actualUser);
        return "board_id";
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MODO" })
    @GetMapping("/create")
    public String createBoardForm(Model model) {
        model.addAttribute(BOARD_PARAM_NAME, new Board());
        return BOARD_PARAM_FORM;
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MODO" })
    @GetMapping("/{id}/edit")
    public String modifyBoardForm(Model model, @PathVariable Long id) {
        model.addAttribute(BOARD_PARAM_NAME, boardService.find(id));
        return BOARD_PARAM_FORM;
    }

    @GetMapping("")
    public String allBoards(Model model) {
        model.addAttribute("boards", boardService.findAll());
        return "boards";
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MODO" })
    @GetMapping("/{id}/delete")
    public String deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/boards";
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MODO" })
    @PostMapping("")
    public String createBoard(@ModelAttribute @Validated Board board, BindingResult errors) {
        if (errors.hasErrors()) {
            return BOARD_PARAM_FORM;
        }
        boardService.save(board);
        return "redirect:boards/" + board.getId();
    }
}
