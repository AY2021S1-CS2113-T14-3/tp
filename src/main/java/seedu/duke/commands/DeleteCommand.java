package seedu.duke.commands;

import seedu.duke.DukeException;
import seedu.duke.common.Messages;
import seedu.duke.task.ItemList;
import seedu.duke.task.LinkList;
import seedu.duke.task.ListType;
import seedu.duke.task.TaskList;
import seedu.duke.task.Task;

import java.util.Map;
import java.util.ArrayList;

// @@author MuhammadHoze

/**
 * Deletes a Task identified by its index in the task list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the task listing.\n"
            + "     Parameters: INDEX\n"
            + "     Example: " + COMMAND_WORD + " task" + " 1\n"
            + "          Optional parameter 1: p/PRIORITY\n"
            + "             Deletes all the tasks with PRIORITY.\n"
            + "             Example: " + COMMAND_WORD + " tasks" + " p/1\n"
            + "          Optional parameter 2: c/CATEGORY\n"
            + "             Deletes all the tasks with CATEGORY.\n"
            + "             Example: " + COMMAND_WORD + " tasks" + " c/cs2113.\n"
            + "     Deletes the link identified by the index number used in the link listing.\n"
            + "     Parameters: INDEX\n"
            + "     Example: " + COMMAND_WORD + " link" + " 1\n";

    private boolean hasPriorityValue = false;
    private boolean hasCategoryValue = false;
    private String categoryValue = "";
    private int index;
    private int priorityIndex;
    private boolean isLink;


    public DeleteCommand(int index) {
        assert index > 0 : "Task number should be greater than 0";
        this.hasPriorityValue = false;
        this.hasCategoryValue = false;
        this.index = index;
    }

    public DeleteCommand(String inputValue) {
        if (inputValue.startsWith("p")) {
            this.hasPriorityValue = true;
            this.priorityIndex = Integer.parseInt(inputValue.substring(2));
        } else if (inputValue.startsWith("c")) {
            this.hasCategoryValue = true;
            this.categoryValue = inputValue.substring(2);
        }
    }

    public DeleteCommand(int index, boolean isLink) {
        assert index > 0 : "Task number should be greater than 0";
        this.hasPriorityValue = false;
        this.hasCategoryValue = false;
        this.isLink = isLink;
        this.index = index;
    }

    @Override
    public void execute(Map<ListType, ItemList> listMap) throws DukeException {
        TaskList tasks = (TaskList) listMap.get(ListType.TASK_LIST);
        LinkList links = (LinkList) listMap.get(ListType.LINK_LIST);
        ArrayList<Task> taskDeleted = new ArrayList<Task>();
        boolean isCategory = false;

        if (hasPriorityValue) {
            if (priorityIndex < 0) {
                throw new DukeException(Messages.EXCEPTION_INVALID_PRIORITY);
            }
            for (int i = tasks.size() - 1; i >= 0; i--) {
                if (tasks.get(i).getPriority() == priorityIndex) {
                    taskDeleted.add(tasks.get(i));
                    tasks.deletePriorityOrCategoryTask(i);
                }
            }
            if (taskDeleted.isEmpty()) {
                throw new DukeException(Messages.EXCEPTION_INVALID_PRIORITY);
            }
            tasks.displayDeletedPriorityOrCategoryTask(taskDeleted, isCategory);
        } else if (hasCategoryValue) {
            isCategory = true;
            for (int i = tasks.size() - 1; i >= 0; i--) {
                if (tasks.get(i).getCategory() == null) {
                    continue; //ignore if category is not set for the task
                }
                if (tasks.get(i).getCategory().equals(categoryValue)) {
                    taskDeleted.add(tasks.get(i));
                    tasks.deletePriorityOrCategoryTask(i);
                }
            }
            if (taskDeleted.isEmpty()) {
                throw new DukeException(Messages.EXCEPTION_CATEGORY_NOT_FOUND);
            }
            tasks.displayDeletedPriorityOrCategoryTask(taskDeleted, isCategory);
        } else if (isLink) {
            links.deleteLink(index);
        } else {
            tasks.deleteTask(index);
        }
    }
}
