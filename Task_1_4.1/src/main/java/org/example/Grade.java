package org.example;

/**
 * Represents the possible grades in a student's grade book.
 */
public enum Grade {
    /** Fail (0). */
    F,
    /** Pass (1). */
    P,
    /** Unsatisfactory (2). */
    D,
    /** Satisfactory (3). */
    C,
    /** Good (4). */
    B,
    /** Excellent (5). */
    A;

    /**
     * Checks if the grade has a numeric value (applicable for exams and differentiated credits).
     *
     * @return {@code true} if the grade is numeric, {@code false} otherwise
     */
    public boolean isNotPassFail() {
        return this == A || this == B || this == C || this == D;
    }

    /**
     * Checks if the grade is positive (no unsatisfactory grades or fails).
     *
     * @return {@code true} if the grade is positive, {@code false} otherwise
     */
    public boolean isPositive() {
        return this == A || this == B || this == C || this == P;
    }

    /**
     * Returns the numeric value of the grade.
     *
     * @return the numeric value from 2 to 5
     * @throws IllegalStateException if the grade does not have a numeric value
     */
    public int getValue() {
        if (!isNotPassFail()) {
            throw new IllegalStateException("Grade does not have a numeric value");
        }
        return ordinal();
    }
}
