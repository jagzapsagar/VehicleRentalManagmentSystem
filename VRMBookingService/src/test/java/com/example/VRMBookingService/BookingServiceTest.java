package com.example.VRMBookingService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import com.example.VRMBookingService.repository.BookingRepository;
import com.example.VRMBookingService.service.BookingService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
	
	@Autowired
	private BookingService bookingService;
	
	@MockBean
    private BookingRepository bookingRepository;
	
	@Mock
    private RestTemplate restTemplate;
	
	@Test
	void testCount() {
		
		when(bookingRepository.count()).thenReturn(5L); //
		 long count = bookingService.countBookings();
		assertEquals(5, count);
		assertTrue(5==5);
	}
	
	@Test
	void testMethod() {
		
		
		String str = new String("sgr");
		String str2 = new String("sgr");
		assertEquals(str, str2);  // return True
		assertSame(str, str2);    // return false
		
		
		//assertTrue(5==5);
	}
	
	@Test
    void testCancelBooking_BookingNotFound() {
        // Arrange
        Long bookingId = 99L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.cancelBooking(bookingId);
        });

        assertEquals("Booking not found", exception.getMessage());
        verify(bookingRepository).findById(bookingId);
        verifyNoMoreInteractions(bookingRepository, restTemplate);
    }
	
	

}
