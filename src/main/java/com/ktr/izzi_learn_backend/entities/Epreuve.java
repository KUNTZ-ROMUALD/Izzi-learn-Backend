package com.ktr.izzi_learn_backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder @Getter @Setter
public class Epreuve {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date createdDateAt;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String corrected;
    @PrePersist
    protected void onCreate() {
        this.createdDateAt = new Date();
    }

}
