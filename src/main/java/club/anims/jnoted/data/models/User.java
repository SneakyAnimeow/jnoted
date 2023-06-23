package club.anims.jnoted.data.models;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "User", indexes = {
        @Index(name = "idx_user_name", columnList = "name"),
        @Index(name = "idx_user_email", columnList = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "hash", nullable = false, length = 128)
    private String hash;

    @Column(name = "name", nullable = false, unique = true, length = 32)
    private String name;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    @Column(name = "email", unique = true, length = 320)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public User(String name, String hash) {
        this.name = name;
        this.hash = hash;
        this.joinDate = LocalDateTime.now();
    }

    public User(String name, String hash, String email) {
        this.name = name;
        this.hash = hash;
        this.joinDate = LocalDateTime.now();
        this.email = email;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
