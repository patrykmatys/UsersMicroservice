package org.thesis.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

    @Id
    private String email;

    private String password;
    private String name;
    private String surname;
    private String address;
}
