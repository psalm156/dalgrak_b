package springbootApplication.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Entity
@Builder
@AllArgsConstructor
public class Cover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String coverImageUrl;

    @CreationTimestamp
    private Timestamp createDate;
}