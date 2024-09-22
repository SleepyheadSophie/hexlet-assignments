package exercise.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import lombok.Getter;
import lombok.Setter;

// BEGIN
@Entity
@Table(name = "person")
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(unique = true)
    private Long id;

    private String firstName;

    private String lastName;
}

// END
