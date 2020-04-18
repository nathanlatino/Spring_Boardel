package ch.hearc.smb.controller;


import ch.hearc.smb.model.Comment;
import ch.hearc.smb.model.Post;
import ch.hearc.smb.service.CommentService;
import ch.hearc.smb.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    PostService postService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODO') or #comment.user.username == authentication.name")
    @PostMapping("/delete")
    public String deleteComment(@RequestParam Comment comment) {
        Long idPost = comment.getPost().getId();
        commentService.delete(comment);
        return "redirect:/posts/" + idPost;
    }

    @GetMapping(value = "/{boardid}", produces = "application/json")
    public @ResponseBody
    Map<String, Object> getComments(@PathVariable Long boardid, @PageableDefault(value = 5, page = 0) Pageable pageable) {
        Page<Comment> comments = commentService.getAllPostByDesc(boardid, pageable);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("comments", comments);
        objectMap.put("commentDates",comments.stream().map(Comment::getDateDisplay).collect(Collectors.toList()));
        objectMap.put("customUsers", comments.stream().map(c -> c.getUser().getUsername()).collect(Collectors.toList()));
        objectMap.put("idUser", comments.stream().map(c -> c.getUser().getId()).collect(Collectors.toList()));
        return objectMap;
    }

    @PostMapping("")
    public String createComments(@ModelAttribute @Validated Comment comment, BindingResult errors) {
        if (errors.hasErrors()) {
            return "redirect:posts/" + comment.getPost().getId() + "?error=1";
        }
        commentService.save(comment);
        Post post = comment.getPost();
        post.setModifiedDate(comment.getCreatedDate());
        postService.save(post);
        return "redirect:posts/" + comment.getPost().getId();
    }
}
