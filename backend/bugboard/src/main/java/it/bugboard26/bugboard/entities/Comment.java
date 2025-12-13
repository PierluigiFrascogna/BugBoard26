package it.bugboard26.bugboard.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comments")
@PrimaryKeyJoinColumn(name = "uuid")
public class Comment extends IssueEvent {
    @Column(name = "text")
    private String text;
}
