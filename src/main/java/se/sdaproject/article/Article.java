package se.sdaproject.article;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import se.sdaproject.comment.Comment;
import se.sdaproject.topic.Topic;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;

    private String authorName;

    @OneToMany(mappedBy = "commentedArticle")
    private List<Comment> articleCommentList;

    @ManyToMany(mappedBy = "articlesList")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Topic> topicList;

    public Article(){}

    public Article(String title, String body, String authorName) {
        this.title = title;
        this.body = body;
        this.authorName = authorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Topic> getTopicsList() {
        return topicList;
    }

    public void setTopicsList(Set<Topic> topicList) {
        this.topicList = topicList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void updateArticle(Article updatedArticle){
        this.title = updatedArticle.title;
        this.authorName=updatedArticle.authorName;
        this.body=updatedArticle.body;
    }

    public List<Comment> getArticleCommentsList() {
        return articleCommentList;
    }


    public void setArticleCommentsList(List<Comment> articleCommentList) {
        this.articleCommentList = articleCommentList;
    }
}