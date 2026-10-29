package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for increased scholarship eligibility.
 */
class ScholarshipTest extends BaseGradeBookTest {

    @Test
    void testScholarshipNoSemesters() {
        assertFalse(book.canGetIncreasedScholarship());
    }

    @Test
    void testScholarshipEmptyCurrentSemester() {
        book.startNewSemester();
        assertFalse(book.canGetIncreasedScholarship());
    }

    @Test
    void testScholarshipSuccess() {
        book.startNewSemester();
        add(AssessmentType.EXAM, Grade.A);
        add(AssessmentType.EXAM, Grade.B);
        add(AssessmentType.PASS_FAIL_CREDIT, Grade.P);

        assertTrue(book.canGetIncreasedScholarship());
    }

    @Test
    void testScholarshipFailSatisfactory() {
        book.startNewSemester();
        add(AssessmentType.EXAM, Grade.A);
        add(AssessmentType.EXAM, Grade.C);

        assertFalse(book.canGetIncreasedScholarship());
    }

    @Test
    void testScholarshipFailOnCredit() {
        book.startNewSemester();
        add(AssessmentType.EXAM, Grade.A);
        add(AssessmentType.PASS_FAIL_CREDIT, Grade.F);

        assertFalse(book.canGetIncreasedScholarship());
    }
}