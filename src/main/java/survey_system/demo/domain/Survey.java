package survey_system.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a survey, consisting of a title, description, and associated questions.
 */
@Entity
@Getter @Setter
public class Survey {

    @Id @GeneratedValue
    @Column(name = "survey_id")
    private Long id;

    private String title;
    private String description;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();
}
