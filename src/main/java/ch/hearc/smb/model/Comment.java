package ch.hearc.smb.model;

import ch.hearc.smb.helper.ManageDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    @Size(min = 1, max = 300)
    private String content;

    @NotNull
    @CreatedDate
    private String createdDate;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private CustomUser user;

    public Comment(String content, Post post, CustomUser user) {
        this.content = content;
        this.post = post;
        this.user = user;

        this.createdDate = ManageDate.getInstance().dateToDB(new Date());

    }

    public Comment() {
        this(new Post(), new CustomUser());
    }

    public Comment(Post post, CustomUser user) {
        this("", post, user);
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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


    public String getDateDisplay() {
        return ManageDate.getInstance().dateFromDBToDisplay(createdDate);
    }

    public boolean equals(Object comment) {
        if (comment == this) {
            return true;
        }
        if (!(comment instanceof Comment)) {
            return false;
        }
        return Objects.equals(this.id, ((Comment) comment).id);
    }

    @Override
    public int hashCode() {
        return content.hashCode() * createdDate.hashCode();
    }

}
