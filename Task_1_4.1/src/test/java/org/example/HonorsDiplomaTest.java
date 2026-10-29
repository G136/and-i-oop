package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for predicting the honors diploma.
 */
class HonorsDiplomaTest extends BaseGradeBookTest {

    @Test
    void testHonorsDiplomaEmpty() {
        assertTrue(book.canGetHonorsDiploma());
    }

    @Test
    void testHonorsDiplomaSuccess() {
        book.startNewSemester();
        add(AssessmentType.EXAM, Grade.A);
        add(AssessmentType.EXAM, Grade.A);
        add(AssessmentType.DIFF_CREDIT, Grade.A);
        add(AssessmentType.PASS_FAIL_CREDIT, Grade.P);
        // 3 'A' out of 4 total records = 75%

        assertTrue(book.canGetHonorsDiploma());
    }

    @Test
    void testHonorsDiplomaFailLowPercentage() {
        book.startNewSemester();
        add(AssessmentType.EXAM, Grade.A);
        add(AssessmentType.EXAM, Grade.B);
        add(AssessmentType.EXAM, Grade.B); // 1 out of 3 = 33%

        assertFalse(book.canGetHonorsDiploma());
    }

    @Test
    void testHonorsDiplomaFailSatisfactory() {
        book.startNewSemester();
        add(AssessmentType.EXAM, Grade.A);
        add(AssessmentType.DIFF_CREDIT, Grade.C);

        assertFalse(book.canGetHonorsDiploma());
    }

    @Test
    void testHonorsDiplomaFailOnCredit() {
        book.startNewSemester();
        add(AssessmentType.EXAM, Grade.A);
        add(AssessmentType.PASS_FAIL_CREDIT, Grade.F);

        assertFalse(book.canGetHonorsDiploma());
    }

    @Test
    void testHonorsDiplomaFailBadQualificationWork() {
        book.startNewSemester();
        add(AssessmentType.EXAM, Grade.A);
        addWork(Grade.B);

        assertFalse(book.canGetHonorsDiploma());
    }

    @Test
    void testHonorsDiplomaSuccessWithExcellentQualificationWork() {
        book.startNewSemester();
        add(AssessmentType.EXAM, Grade.A);
        addWork(Grade.A);

        assertTrue(book.canGetHonorsDiploma());
    }
}