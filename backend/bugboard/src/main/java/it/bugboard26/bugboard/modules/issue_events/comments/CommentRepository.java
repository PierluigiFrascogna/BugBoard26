package it.bugboard26.bugboard.modules.issue_events.comments;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import it.bugboard26.bugboard.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    
}
