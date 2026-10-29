package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for calculating the average grade.
 */
class AverageGradeTest extends BaseGradeBookTest {

    @Test
    void testAverageGradeEmptyBook() {
        assertEquals(0.0, book.getAverageGrade(), 0.01);
    }

    @Test
    void testAverageGradeWithNumericAndPassFail() {
        book.startNewSemester();
        add(AssessmentType.EXAM, Grade.A); // 5
        add(AssessmentType.PASS_FAIL_CREDIT, Grade.P); // ignored
        add(AssessmentType.EXAM, Grade.B); // 4

        assertEquals(4.5, book.getAverageGrade(), 0.01);
    }
}