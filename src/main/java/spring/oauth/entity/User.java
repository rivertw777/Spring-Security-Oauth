package spring.oauth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.oauth.enums.Role;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "username", length = 10)
    private String username;

    @Column(name = "password", length = 60)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "roles")
    private List<Role> roles = new ArrayList<>();

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Builder
    public User(String username, String password, List<Role> roles, String email, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}