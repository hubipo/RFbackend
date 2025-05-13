package org.links.consultationservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    @JsonBackReference
    private Conversation conversation;


    private String role;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private LocalDateTime timestamp = LocalDateTime.now();

    public MessageEntity(Conversation conversation, String role, String content) {
        this.conversation = conversation;
        this.role = role;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public MessageEntity() {

    }
}
