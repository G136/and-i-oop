package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for expected exceptions and edge cases.
 */
class ExceptionsTest extends BaseGradeBookTest {

    @Test
    void testAddRecordWithoutStartSemesterThrowsException() {
        assertThrows(IllegalStateException.class, () ->
                add(AssessmentType.EXAM, Grade.A)
        );
    }

    @Test
    void testAddQualificationWorkWithoutStartSemesterThrowsException() {
        assertThrows(IllegalStateException.class, () ->
                addWork(Grade.A)
        );
    }

    @Test
    void testGetNumericValueOnPassThrowsException() {
        assertThrows(IllegalStateException.class, Grade.P::getValue);
    }
}