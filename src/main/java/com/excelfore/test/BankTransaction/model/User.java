package com.excelfore.test.BankTransaction.model;

import com.excelfore.test.BankTransaction.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name; // Real Name Of Person
    @Column(nullable = false, unique = true)
    @NotBlank
    @NotEmpty
    private String username; // Unique Name Of Person Which Is Going To Match With AccountHolderName In Account Model
    @Column(nullable = false)
    @NotEmpty
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; // "USER" or "ADMIN"
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

