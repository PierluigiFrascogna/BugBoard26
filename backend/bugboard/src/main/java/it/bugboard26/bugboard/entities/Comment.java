package it.bugboard26.bugboard.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@PrimaryKeyJoinColumn(name = "uuid")
@Table(name = "comments")
@Entity
public class Comment extends IssueEvent {
    @Column(name = "text")
    private String text;
}
