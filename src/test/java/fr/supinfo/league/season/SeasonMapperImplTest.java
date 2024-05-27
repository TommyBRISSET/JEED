package fr.supinfo.league.season;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SeasonMapperImplTest {

    private final SeasonMapper mapper = new SeasonMapperImpl();

    @Test
    public void testEntityToDto() {
        // Given
        SeasonEntity entity = new SeasonEntity();
        entity.setId(UUID.randomUUID());
        entity.setLabel("Test Season");

        // When
        SeasonDto dto = mapper.entityToDto(entity);

        // Then
        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getLabel(), dto.label());

        // Test with null entity
        assertNull(mapper.entityToDto(null));
    }

    @Test
    public void testDtoToEntity() {
        // Given
        SeasonDto dto = new SeasonDto(UUID.randomUUID(), "Test Season");

        // When
        SeasonEntity entity = mapper.dtoToEntity(dto);

        // Then
        assertEquals(dto.id(), entity.getId());
        assertEquals(dto.label(), entity.getLabel());

        // Test with null dto
        assertNull(mapper.dtoToEntity(null));
    }
}
