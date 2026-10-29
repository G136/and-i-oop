package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GradeBookTest {

    private GradeBook book;

    @BeforeEach
    void setUp() {
        book = new GradeBook(true);
    }

    private void add(final AssessmentType type, final Grade grade) {
        book.addRecord("Test Discipline", type, grade);
    }

    private void addWork(final Grade grade) {
        book.addQualificationWork(grade);
    }


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
        // 3 EXCELLENT out of 4 total records = 75%

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