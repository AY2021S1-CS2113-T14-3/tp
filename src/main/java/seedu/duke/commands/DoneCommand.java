package seedu.duke.commands;

import seedu.duke.DukeException;
import seedu.duke.model.Model;
import seedu.duke.model.ListType;
import seedu.duke.model.itemlist.ModuleList;
import seedu.duke.model.itemlist.TaskList;

/**
 * Marks a Task, identified by its index in the task list, as done.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the task listing as done.\n"
            + "     Parameters: INDEX\n"
            + "     Example: " + COMMAND_WORD + " 1";

    private final ListType doneType;
    private final int index;

    public DoneCommand(int index, ListType doneType) {
        this.index = index;
        this.doneType = doneType;
    }

    @Override
    public void execute(Model model) throws DukeException {
        TaskList tasks = (TaskList) model.getList(ListType.TASK_LIST);
        ModuleList modules = (ModuleList) model.getList(ListType.MODULE_LIST);
        switch (doneType) {
        case TASK_LIST:
            tasks.markTaskAsDone(index);
            break;
        case MODULE_LIST:
            modules.markTaskAsDone(index);
            break;
        }
    }
}
