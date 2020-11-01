package seedu.duke.task;

import seedu.duke.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a module.
 */
public class Module extends Item {
    public static final Pattern MODULE_CODE_PATTERN = Pattern.compile("(^[A-Z]{2,3}[\\d]{4}[A-Z]?$)");
    public static final Pattern MODULE_SEM_PATTERN = Pattern.compile("(^[\\d]{4}S[12]$)");

    private final String grade;
    private final double gradePoint;
    private final int mc;
    private final String semester;

    /**
     * Constructor used when adding a new task.
     * By default, the deadline task is not done.
     *
     * @param moduleCode the description of the task
     */
    public Module(String moduleCode, String grade, int mc, String semester) throws DukeException {
        super(moduleCode);

        this.grade = grade;
        this.mc = mc;
        this.semester = semester;
        gradePoint = getCapFromGrade(grade);

        Matcher matcher = MODULE_CODE_PATTERN.matcher(moduleCode);
        if (!matcher.find()) {
            throw new DukeException("Your module code is wrong!");
        }
        matcher = MODULE_SEM_PATTERN.matcher(semester);
        if (!matcher.find()) {
            throw new DukeException("Your semester code is wrong!");
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%d MC) (AY%s)", getGrade(), getDescription(), getMc(), getSemester());
    }

    /**
     * Converts the attributes of the task into a formatted string to be saved into the storage file.
     *
     * @return the formatted string to be saved into the storage file
     */
    @Override
    public String toFile() {
        return getDescription() + " | " + getGrade() + " | " + getMc() + " | " + getSemester();
    }

    public int getMc() {
        return mc;
    }

    public String getGrade() {
        return grade;
    }

    public double getGradePoint() {
        return gradePoint;
    }

    public String getSemester() {
        return semester;
    }

    private double getCapFromGrade(String grade) throws DukeException {
        switch (grade) {
        case "A+":
            // Fallthrough
        case "A":
            return 5.0;
        case "A-":
            return 4.5;
        case "B+":
            return 4.0;
        case "B":
            return 3.5;
        case "B-":
            return 3.0;
        case "C+":
            return 2.5;
        case "C":
            return 2.0;
        case "D+":
            return 1.5;
        case "D":
            return 1.0;
        case "S":
            // Fallthrough
        case "U":
            // Fallthrough
        case "F":
            return 0.0;
        default:
            throw new DukeException("Invalid grade!");
        }
    }
}
