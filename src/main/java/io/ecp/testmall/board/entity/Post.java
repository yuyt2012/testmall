package io.ecp.testmall.board.entity;

import io.ecp.testmall.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    private String writer;
    private String title;
    private String password;
    @Length(max = 1000)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @PrePersist
    protected void onRegDate() {
        regDate = new Date();
        updateDate = new Date();
    }

    @PreUpdate
    protected void onUpdateDate() {
        updateDate = new Date();
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setPost(null);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public void removeMember(Member member) {
        member.getPosts().remove(this);
        this.member = null;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void deletePost() {
        this.member.getPosts().remove(this);
        this.member = null;
    }
}
