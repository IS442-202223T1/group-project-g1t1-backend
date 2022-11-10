package com.is442project.cpa.booking;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class BookingConfig {

    @Id
    private String attribute;

    @NotNull
    private String value;

    public BookingConfig() {

    }

    public BookingConfig(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }
}
