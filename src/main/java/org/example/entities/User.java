package org.example.entities;

import java.util.UUID;

public class User {

    private UUID uuid;
    private String name;
    private String email;
    private String password;


    public User(UUID uuid, String name, String email, String password) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password) {
        this.uuid = UUID.randomUUID(); // Gera um UUID único para o usuário
        this.name = name;
        this.email = email;
        this.password = password;
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
