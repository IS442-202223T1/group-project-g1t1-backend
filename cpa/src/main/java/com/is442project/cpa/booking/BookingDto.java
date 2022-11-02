package com.is442project.cpa.booking;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class BookingDto {
    private LocalDate Date;
    private String email;
    private List<String> corporatePassId;


    public BookingDto() {
    }

    public BookingDto(LocalDate date, String email, List<String> corporatePassId) {
        Date = date;
        this.email = email;
        this.corporatePassId = corporatePassId;
    }



    @Override
    public String toString() {
        return "BookingDto{" +
                "Date=" + Date +
                ", email='" + email + '\'' +
                '}';
    }
}
