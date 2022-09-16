package ru.nova.novalib.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor()
@Entity
@Table(name = "nl_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Roles userRole;
    @Column(name = "user_created")
    private LocalDate userCreated;

    public enum Roles {
        ADMIN, USER
    }

}
