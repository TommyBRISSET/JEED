package fr.supinfo.league;

import fr.supinfo.league.RestExceptionHandler;
import fr.supinfo.league.season.SeasonNotEmptyException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionHandlerTest {

    @Test
    public void testHandleSeasonNotEmptyException() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        SeasonNotEmptyException ex = new SeasonNotEmptyException("Test Season");
        RestExceptionHandler exceptionHandler = new RestExceptionHandler();

        // When
        ResponseEntity<Object> responseEntity = exceptionHandler.handleSeasonNotEmptyException(ex);

        // Then
        assertEquals(HttpStatus.LOCKED, responseEntity.getStatusCode());
        assertEquals("Impossible de supprimer la saison Test Season, en effet, elle contient encore des définitions de journées", responseEntity.getBody());
    }

    @Test
    public void testHandleIllegalArgumentException() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        IllegalArgumentException ex = new IllegalArgumentException("Illegal argument exception message");
        RestExceptionHandler exceptionHandler = new RestExceptionHandler();

        // When
        ResponseEntity<Object> responseEntity = exceptionHandler.handleIllegalArgumentException(ex);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Illegal argument exception message", responseEntity.getBody());
    }
}
