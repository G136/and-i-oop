package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for transferring from paid to budget education.
 */
class TransferToBudgetTest extends BaseGradeBookTest {

    @Test
    void testTransferNotPaidEducation() {
        book.setPaidEducation(false);
        book.startNewSemester();
        add(AssessmentType.EXAM, Grade.A);

        assertFalse(book.canTransferToBudget());
    }

    @Test
    void testTransferSuccess() {
        book.startNewSemester(); // Semester 1
        add(AssessmentType.EXAM, Grade.B);

        book.startNewSemester(); // Semester 2
        add(AssessmentType.EXAM, Grade.A);
        add(AssessmentType.DIFF_CREDIT, Grade.C); // Allowed
        add(AssessmentType.PASS_FAIL_CREDIT, Grade.P);

        assertTrue(book.canTransferToBudget());
    }

    @Test
    void testTransferFailDueToSatisfactoryExam() {
        book.startNewSemester();
        add(AssessmentType.EXAM, Grade.C);

        assertFalse(book.canTransferToBudget());
    }

    @Test
    void testTransferFailDueToFailCredit() {
        book.startNewSemester();
        add(AssessmentType.EXAM, Grade.A);
        add(AssessmentType.PASS_FAIL_CREDIT, Grade.F);

        assertFalse(book.canTransferToBudget());
    }

    @Test
    void testTransferIgnoresOldSemesters() {
        book.startNewSemester(); // Semester 1
        add(AssessmentType.EXAM, Grade.C); // Bad grade in the past

        book.startNewSemester(); // Semester 2
        add(AssessmentType.EXAM, Grade.A);

        book.startNewSemester(); // Semester 3
        add(AssessmentType.EXAM, Grade.A);

        // Only semesters 2 and 3 are checked, so it should be true
        assertTrue(book.canTransferToBudget());
    }
}