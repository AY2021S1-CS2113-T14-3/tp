package seedu.duke.commands;

import org.junit.jupiter.api.Test;
import seedu.duke.DukeException;
import seedu.duke.model.Model;
import seedu.duke.model.ListType;
import seedu.duke.model.item.Task;
import seedu.duke.model.itemlist.TaskList;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddCommandTest {
    private static final String TEST_DESCRIPTION = "test description";
    private HashMap<String, String> argumentsMap;
    private Model model;
    private TaskList taskList;

    /**
     * Resets the fields for each test case.
     */
    void resetFields() {
        model = new Model();
        argumentsMap = new HashMap<>();
        taskList = (TaskList) model.getList(ListType.TASK_LIST);
    }

    @Test
    void execute_validCommand_addsTodo() throws DukeException {
        resetFields();

        new AddCommand(TEST_DESCRIPTION, argumentsMap, ListType.TASK_LIST).execute(model);
        assertEquals(1, taskList.size());
        assertEquals(TEST_DESCRIPTION, taskList.get(0).getDescription());
    }

    @Test
    void execute_validCommandWithPriority_addsTodoWithPriority() throws DukeException {
        resetFields();

        String inputPriority = "2";
        argumentsMap.put("p", inputPriority);

        new AddCommand(TEST_DESCRIPTION, argumentsMap, ListType.TASK_LIST).execute(model);
        assertEquals(1, taskList.size());
        assertEquals(Integer.parseInt(inputPriority), taskList.get(0).getPriority());
    }

    @Test
    void execute_validCommandWithCategory_addsTodoWithCategory() throws DukeException {
        resetFields();

        String inputCategory = "cs2113";
        argumentsMap.put("c", inputCategory);

        new AddCommand(TEST_DESCRIPTION, argumentsMap, ListType.TASK_LIST).execute(model);
        assertEquals(1, taskList.size());
        assertEquals(inputCategory, taskList.get(0).getCategory());
    }

    @Test
    void execute_commandWithInvalidPriority_throwsException() {
        resetFields();

        String inputPriority = "-2";
        argumentsMap.put("p", inputPriority);

        assertThrows(DukeException.class, () ->
                new AddCommand(TEST_DESCRIPTION, argumentsMap, ListType.TASK_LIST).execute(model));

        inputPriority = "a";
        argumentsMap.put("p", inputPriority);
        assertThrows(DukeException.class, () ->
                new AddCommand(TEST_DESCRIPTION, argumentsMap, ListType.TASK_LIST).execute(model));
    }

    @Test
    void execute_commandWithDate_addsCommandWithDate() throws DukeException {
        resetFields();

        String inputDate = "13-05-2020";
        String expectedDateString = "13 May 2020";
        argumentsMap.put("date", inputDate);

        new AddCommand(TEST_DESCRIPTION, argumentsMap, ListType.TASK_LIST).execute(model);
        assertEquals(expectedDateString, taskList.get(0).getDateString(Task.DATETIME_PRINT_FORMAT));
    }

    @Test
    void execute_commandWithInvalidDate_throwsException() {
        resetFields();

        String inputDate = "13-13-2020";
        argumentsMap.put("date", inputDate);
        assertThrows(DukeException.class, () ->
                new AddCommand(TEST_DESCRIPTION, argumentsMap, ListType.TASK_LIST).execute(model));

        inputDate = "blah";
        argumentsMap.put("date", inputDate);
        assertThrows(DukeException.class, () ->
                new AddCommand(TEST_DESCRIPTION, argumentsMap, ListType.TASK_LIST).execute(model));
    }
}
