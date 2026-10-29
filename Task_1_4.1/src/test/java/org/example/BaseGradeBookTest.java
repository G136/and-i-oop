package org.example;

import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for GradeBook tests.
 * Provides common setup and helper methods to avoid code duplication.
 */
abstract class BaseGradeBookTest {

    protected GradeBook book;

    @BeforeEach
    void setUp() {
        book = new GradeBook(true);
    }

    /**
     * Helper to add a standard record to the current semester.
     *
     * @param type  the assessment type
     * @param grade the grade received
     */
    protected void add(final AssessmentType type, final Grade grade) {
        book.addRecord("Test Discipline", type, grade);
    }

    /**
     * Helper to add a qualification work record.
     *
     * @param grade the grade received for the defense
     */
    protected void addWork(final Grade grade) {
        book.addQualificationWork(grade);
    }
}