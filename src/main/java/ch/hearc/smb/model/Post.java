package ch.hearc.smb.model;

import ch.hearc.smb.helper.ManageDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    @NotNull
    @Size(min = 1, max = 300)
    private String content;

    @NotNull
    @LastModifiedDate
    private String modifiedDate;

    @NotNull
    @CreatedDate
    private String createdDate;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private CustomUser user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;


    public Post(String name, String content, Board board, CustomUser user) {
        this.name = name;
        this.content = content;
        this.board = board;
        this.comments = new ArrayList<>();
        this.user = user;

        this.createdDate = ManageDate.getInstance().dateToDB(new Date());

        this.modifiedDate = this.createdDate;
    }

    public Post() {
        this(new Board(), new CustomUser());
    }

    public Post(Board board, CustomUser user) {
        this("", "", board, user);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }


    public String getDateDisplay() {
        return ManageDate.getInstance().dateFromDBToDisplay(modifiedDate);
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean equals(Object post) {
        if (post == this) { return true; }
        if (!(post instanceof Post)) { return false; }
        return Objects.equals(this.id, ((Post) post).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
