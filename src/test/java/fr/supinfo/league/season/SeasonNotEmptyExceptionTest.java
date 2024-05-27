package fr.supinfo.league.season;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SeasonNotEmptyExceptionTest {

    @Test
    public void testSeasonNotEmptyException() {
        assertThrows(SeasonNotEmptyException.class, () -> {
            throw new SeasonNotEmptyException("Season is not empty");
        });
    }
}
