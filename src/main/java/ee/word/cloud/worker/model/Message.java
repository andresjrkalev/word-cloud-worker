package ee.word.cloud.worker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    @JoinColumn
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Occurrence> occurrences;

    public Message(String value, Set<Occurrence> occurrences) {
        this.value = value;
        this.occurrences = occurrences;
    }
}
