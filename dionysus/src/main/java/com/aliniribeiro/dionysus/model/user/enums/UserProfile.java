package com.aliniribeiro.dionysus.model.user.enums;

public enum UserProfile {

    ROLE_ADMIN("Administrador do sistema"),
    ROLE_USER("Usuário");


    private String description;

    UserProfile(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }


}
