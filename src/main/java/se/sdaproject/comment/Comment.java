package se.sdaproject.comment;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import se.sdaproject.article.Article;

import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String authorName;

    @Column(nullable = false)
    private String body;


    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(nullable = false)
    @NotNull
    private Article commentedArticle;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void updateComment(Comment updatedArticleComment){
        this.authorName = updatedArticleComment.authorName;
        this.body = updatedArticleComment.body;
    }

    public Article getCommentedArticle() {
        return commentedArticle;
    }

    public void setCommentedArticle(Article commentedArticle) {
        this.commentedArticle = commentedArticle;
    }
}