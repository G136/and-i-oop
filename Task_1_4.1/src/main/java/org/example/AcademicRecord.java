package org.example;

/** Represents a single academic record in the grade book. */
public record AcademicRecord(String discipline,
                             AssessmentType type,
                             Grade grade,
                             int semesterNumber) {}