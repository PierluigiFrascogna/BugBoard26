package it.bugboard26.bugboard.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@PrimaryKeyJoinColumn(name = "uuid")
@Table(name = "description_changes")
@Entity
public class DescriptionChange extends Change {
    @Column(name = "old")
    private String oldDescription;

    @Column(name = "new")
    private String newDescription;
    
}
