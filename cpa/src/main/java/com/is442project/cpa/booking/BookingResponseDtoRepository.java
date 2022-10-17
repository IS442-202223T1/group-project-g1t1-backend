package com.is442project.cpa.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingResponseDtoRepository extends JpaRepository<BookingResponseDto, String> {
    
}
