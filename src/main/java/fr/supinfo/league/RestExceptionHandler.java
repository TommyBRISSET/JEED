package fr.supinfo.league;

import fr.supinfo.league.season.SeasonNotEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {SeasonNotEmptyException.class})
    public ResponseEntity<Object> handleSeasonNotEmptyException(SeasonNotEmptyException ex) {
        String message = "Impossible de supprimer la saison " + ex.getMessage() +
                ", en effet, elle contient encore des définitions de journées";
        return ResponseEntity.status(HttpStatus.LOCKED).body(message);
    }
}
