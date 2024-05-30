package ru.onebeattrue.entities;

import lombok.*;
import ru.onebeattrue.models.Color;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents a cat entity with its attributes such as name, birthdate, breed, color, master, and list of friends.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cats")
public class Cat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(nullable = false)
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private Master master;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(

            name = "friendship",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<Cat> friends;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Cat cat = (Cat) obj;
        return Objects.equals(id, cat.id);
    }
}