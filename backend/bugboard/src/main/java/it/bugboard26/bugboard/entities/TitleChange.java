package it.bugboard26.bugboard.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@PrimaryKeyJoinColumn(name = "uuid")
@Table(name = "title_changes")
@Entity
public class TitleChange extends Change {
    @Column(name = "old")
    private String oldTitle;
    
    @Column(name = "new")
    private String newTitle;
}
