package com.internship.iam.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "privileges")
public class Privilege {

    @Id
    private String id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "privilege")
    private List<RolePrivilege> rolesPrivileges;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RolePrivilege> getRolesPrivileges() {
        return rolesPrivileges;
    }

    public void setRolesPrivileges(List<RolePrivilege> rolesPrivileges) {
        this.rolesPrivileges = rolesPrivileges;
    }
}
