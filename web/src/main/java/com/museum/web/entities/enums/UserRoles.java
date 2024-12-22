package com.museum.web.entities.enums;

public enum UserRoles {
    GUEST(1), USER(2), MODERATOR(3), ADMIN(4);

    private int value;

    UserRoles(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserRoles fromValue(int value) {
        for (UserRoles role : values()) {
            if (role.value == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
