package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a student's electronic grade book.
 * Provides calculations for average grade, budget transfer, honors diploma, and scholarship.
 */
public class GradeBook {

    private static final double HONORS_DIPLOMA_PERCENTAGE = 75.0;

    private final List<List<AcademicRecord>> semesters = new ArrayList<>();
    private final List<AcademicRecord> allRecords = new ArrayList<>();
    private boolean isPaidEducation;
    private int currentSemester = 0;

    /**
     * Constructs a grade book.
     *
     * @param paidEducation {@code true} if the student studies on a paid basis
     */
    public GradeBook(final boolean paidEducation) {
        this.isPaidEducation = paidEducation;
    }

    /**
     * Starts a new semester.
     */
    public void startNewSemester() {
        currentSemester++;
        semesters.add(new ArrayList<>());
    }

    /**
     * Adds a discipline record to the current semester.
     *
     * @param disciplineName the name of the discipline
     * @param assessmentType the type of assessment
     * @param receivedGrade  the received grade
     * @throws IllegalStateException if a semester has not been started yet
     */
    public void addRecord(final String disciplineName, final AssessmentType assessmentType,
                          final Grade receivedGrade) {
        if (currentSemester == 0) {
            throw new IllegalStateException("Start a semester first");
        }
        AcademicRecord record = new AcademicRecord(disciplineName, assessmentType,
                receivedGrade, currentSemester);
        semesters.get(semesters.size() - 1).add(record);
        allRecords.add(record);
    }

    /**
     * Adds a qualification work defense record to the current semester.
     *
     * @param receivedGrade the grade received for the defense
     */
    public void addQualificationWork(final Grade receivedGrade) {
        if (currentSemester == 0) {
            throw new IllegalStateException("Start a semester first");
        }
        addRecord("Qualification Work", AssessmentType.QUALIFICATION_WORK, receivedGrade);
    }

    /**
     * Sets the education form.
     *
     * @param paid {@code true} if the education is paid
     */
    public void setPaidEducation(final boolean paid) {
        this.isPaidEducation = paid;
    }

    /**
     * Calculates the current average grade for the entire study period.
     * Non-differentiated credits are ignored.
     *
     * @return the average grade, or 0.0 if there are no numeric grades
     */
    public double getAverageGrade() {
        return allRecords.stream()
                .filter(record -> record.grade().isNotPassFail())
                .mapToInt(record -> record.grade().getValue())
                .average()
                .orElse(0.0);
    }

    /**
     * Checks if the student can transfer from paid to budget education.
     * Only the last two examination sessions are considered.
     *
     * @return {@code true} if the transfer is possible, {@code false} otherwise
     */
    public boolean canTransferToBudget() {
        if (!isPaidEducation) {
            return false;
        }

        int startSemester = Math.max(1, currentSemester - 1);
        List<AcademicRecord> lastTwoSessions = allRecords.stream()
                .filter(record -> record.semesterNumber() >= startSemester)
                .collect(Collectors.toList());

        boolean noSatisfactoryExams = !hasGrade(lastTwoSessions, Grade.C,
                AssessmentType.EXAM);
        boolean noFails = !hasGrade(lastTwoSessions, Grade.F,
                AssessmentType.PASS_FAIL_CREDIT);

        return noSatisfactoryExams && noFails;
    }

    /**
     * Predicts the possibility of receiving an honors diploma.
     * The function can be used before the completion of studies.
     *
     * @return {@code true} if the student is on track for an honors diploma, else {@code false}
     */
    public boolean canGetHonorsDiploma() {
        boolean qualificationOk = !hasGrade(allRecords, Grade.B,
                AssessmentType.QUALIFICATION_WORK)
                && !hasGrade(allRecords, Grade.C,
                AssessmentType.QUALIFICATION_WORK)
                && !hasGrade(allRecords, Grade.D,
                AssessmentType.QUALIFICATION_WORK);

        if (!qualificationOk) {
            return false;
        }

        boolean hasBadGrades = hasGrade(allRecords, Grade.C,
                AssessmentType.EXAM, AssessmentType.DIFF_CREDIT)
                || hasGrade(allRecords, Grade.D,
                AssessmentType.EXAM, AssessmentType.DIFF_CREDIT)
                || hasGrade(allRecords, Grade.F, AssessmentType.PASS_FAIL_CREDIT);

        if (hasBadGrades) {
            return false;
        }

        List<AcademicRecord> diplomaRecords = allRecords.stream()
                .filter(record -> record.type() != AssessmentType.QUALIFICATION_WORK)
                .toList();

        if (diplomaRecords.isEmpty()) {
            return true;
        }

        long total = diplomaRecords.size();
        long excellentCount = diplomaRecords.stream()
                .filter(record -> record.grade() == Grade.A)
                .count();

        return (excellentCount * 100.0 / total) >= HONORS_DIPLOMA_PERCENTAGE;
    }

    /**
     * Checks if the student is eligible for an increased scholarship next semester.
     * Only the results of the current semester are evaluated.
     *
     * @return {@code true} if the student is worth an increased scholarship, else {@code false}
     */
    public boolean canGetIncreasedScholarship() {
        if (currentSemester == 0) {
            return false;
        }

        List<AcademicRecord> currentSemesterRecords =
                Collections.unmodifiableList(semesters.get(semesters.size() - 1));

        if (currentSemesterRecords.isEmpty()) {
            return false;
        }

        boolean allPositive = currentSemesterRecords.stream()
                .allMatch(record -> record.grade().isPositive());

        boolean onlyExcellentAndGood = currentSemesterRecords.stream()
                .filter(record -> record.grade().isNotPassFail())
                .allMatch(record -> record.grade() == Grade.A
                        || record.grade() == Grade.B);

        return allPositive && onlyExcellentAndGood;
    }

    /**
     * Helper method to check if any record matches the specific grade and assessment types.
     *
     * @param records         the list of records to search
     * @param targetGrade     the grade to look for
     * @param assessmentTypes the assessment types to include in the search
     * @return {@code true} if a matching record is found, {@code false} otherwise
     */
    private boolean hasGrade(final List<AcademicRecord> records, final Grade targetGrade,
                             final AssessmentType... assessmentTypes) {
        List<AssessmentType> typesList = Arrays.asList(assessmentTypes);
        return records.stream()
                .filter(record -> typesList.contains(record.type()))
                .anyMatch(record -> record.grade() == targetGrade);
    }
}
