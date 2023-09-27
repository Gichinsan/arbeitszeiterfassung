package de.gichinsan.arbeitszeiterfassung.model;

public enum Roles {
    USER(Names.USER),
    CREATOR(Names.CREATOR),

    EDITOR(Names.EDITOR),

    ADMIN(Names.ADMIN);

    public class Names {
        public static final String USER = "User";
        public static final String CREATOR = "Creator";
        public static final String EDITOR = "Editor";
        public static final String ADMIN = "Admin";
    }

    private final String label;

    Roles(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
