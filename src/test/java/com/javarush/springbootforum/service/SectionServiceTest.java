package com.javarush.springbootforum.service;

import com.javarush.springbootforum.IntegrationBaseTest;
import com.javarush.springbootforum.dto.SectionCreateEditDto;
import com.javarush.springbootforum.dto.SectionReadDto;
import com.javarush.springbootforum.dto.SectionWithoutCategoryListReadDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class SectionServiceTest extends IntegrationBaseTest {

    private final SectionService sectionService;

    @Test
    void findAll() {
        List<SectionReadDto> sectionReadDtoList = sectionService.findAll();
        assertThat(sectionReadDtoList).hasSizeGreaterThan(0);
    }

    @Test
    void findById() {
        Optional<SectionReadDto> sectionReadDto = sectionService.findById(1L);
        assertThat(sectionReadDto).isPresent();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void createIfAdmin() {
        SectionCreateEditDto section = new SectionCreateEditDto("New title", "New description");
        SectionReadDto sectionReadDto = sectionService.create(section);
        assertEquals("New title", sectionReadDto.getTitle());
        assertEquals("New description", sectionReadDto.getDescription());
    }

    @Test
    @WithMockUser(authorities = {"USER", "MODERATOR"})
    void createIfNotAdmin() {
        SectionCreateEditDto section = new SectionCreateEditDto("New title", "New description");
        assertThatThrownBy(() -> sectionService.create(section))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void updateIfAdmin() {
        SectionCreateEditDto section = new SectionCreateEditDto("New title", "New description");
        Optional<SectionWithoutCategoryListReadDto> updatedSection = sectionService.update(1L, section);
        assertThat(updatedSection).isPresent();
        assertEquals("New title", updatedSection.get().getTitle());
        assertEquals("New description", updatedSection.get().getDescription());
    }

    @Test
    @WithMockUser(authorities = {"USER", "MODERATOR"})
    void updateIfNotAdmin() {
        SectionCreateEditDto section = new SectionCreateEditDto("New title", "New description");
        assertThatThrownBy(() -> sectionService.update(1L, section))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deleteSectionIfAdmin() {
        assertTrue(sectionService.delete(1L));
    }

    @Test
    @WithMockUser(authorities = {"USER", "MODERATOR"})
    void deleteSectionIfNotAdmin() {
        assertThatThrownBy(() -> sectionService.delete(1L))
                .isInstanceOf(AccessDeniedException.class);
    }
}