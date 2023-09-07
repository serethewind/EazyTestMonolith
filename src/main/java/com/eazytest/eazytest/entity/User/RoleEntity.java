package com.eazytest.eazytest.entity.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "role_entity")
public class RoleEntity {
    @Id
    private Long id;
    private String name;
}