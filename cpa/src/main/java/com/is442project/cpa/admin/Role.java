package com.is442project.cpa.admin;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
public class Role {
    private String label;

    private boolean isDisabled;
    public Role(){
    }
    public Role(String label) {
        this.label = label;
        this.isDisabled = false;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return isDisabled == role.isDisabled && Objects.equals(label, role.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, isDisabled);
    }
}
