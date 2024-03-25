package uz.studentsproject.aggregation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;


@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FieldOfStudy extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "university_id")
    private Long universityId;


}
