package kr.pe.mayo.domain.dto;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String Role;

    private Role(String s){
        Role = s;
    }

    public String toString(){
        return this.Role;
    }
}
