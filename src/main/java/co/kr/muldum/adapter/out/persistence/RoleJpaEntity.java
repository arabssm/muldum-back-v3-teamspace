package co.kr.muldum.adapter.out.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 255)
    private String role;

    @Column(length = 255)
    private String description;

    protected RoleJpaEntity() {
    }

    public RoleJpaEntity(Long id, String role, String description) {
        this.id = id;
        this.role = role;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getDescription() {
        return description;
    }
}
