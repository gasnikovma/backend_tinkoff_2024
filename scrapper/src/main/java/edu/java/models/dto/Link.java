package edu.java.models.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "link") public class Link {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(name = "uri") private String uri;
    @Column(name = "last_update_at") private OffsetDateTime lastUpdate;
    @Column(name = "last_check_at") private OffsetDateTime lastCheck;
    @ManyToMany(mappedBy = "linkEntities")
    private List<Chat> chatEntities;

    public Link(Long id, String uri, OffsetDateTime lastCheck, OffsetDateTime lastUpdate) {
        this.id = id;
        this.uri = uri;
        this.lastCheck = lastCheck;
        this.lastUpdate = lastUpdate;
    }

}
