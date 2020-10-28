package seedu.duke.commands;

import seedu.duke.DukeException;
import seedu.duke.common.Messages;
import seedu.duke.task.ItemList;
import seedu.duke.task.LinkList;
import seedu.duke.task.ListType;
import seedu.duke.task.Module;
import seedu.duke.task.ModuleList;
import seedu.duke.task.TaskList;
import seedu.duke.task.Link;
import seedu.duke.task.Task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

// @@author iamchenjiajun
/**
 * Represents a command that adds a task to the task list.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a task to the task list.\n"
            + "     Parameters: TASK_NAME <optional arguments>\n"
            + "     Example: " + COMMAND_WORD + " example_task <optional arguments>";
    public static final HashSet<String> TASK_ALLOWED_ARGUMENTS = new HashSet<>(Arrays.asList("p", "c", "date"));
    public static final HashSet<String> LINK_ALLOWED_ARGUMENTS = new HashSet<>(Arrays.asList("m", "t", "u"));
    public static final HashSet<String> MODULE_ALLOWED_ARGUMENTS = new HashSet<>(Arrays.asList("g", "mc", "ay"));

    protected String description;
    protected HashMap<String, String> argumentsMap;
    private final ListType addType;

    public AddCommand(String description, HashMap<String, String> argumentsMap, ListType addType) {
        this.addType = addType;
        this.description = description;
        this.argumentsMap = argumentsMap;
    }

    /**
     * Executes the command.
     *
     * @param listMap a Map object containing all lists
     */
    @Override
    public void execute(Map<ListType, ItemList> listMap) throws DukeException {
        TaskList tasks = (TaskList) listMap.get(ListType.TASK_LIST);
        LinkList links = (LinkList) listMap.get(ListType.LINK_LIST);
        ModuleList modules = (ModuleList) listMap.get(ListType.MODULE_LIST);
        switch (addType) {
        case TASK_LIST:
            executeAddTask(tasks);
            break;
        case LINK_LIST:
            executeAddLink(links);
            break;
        case MODULE_LIST:
            executeAddModule(modules);
            break;
        default:
            throw new DukeException(Messages.EXCEPTION_INVALID_COMMAND);
        }
    }

    private void executeAddTask(TaskList tasks) throws DukeException {
        if (description.equals("")) {
            throw new DukeException(Messages.EXCEPTION_EMPTY_DESCRIPTION);
        }
        Task newTask = new Task(description);
        setTaskProperties(newTask, argumentsMap);
        tasks.addTask(newTask);
    }

    private void executeAddLink(LinkList links) throws DukeException {
        if (!argumentsMap.containsKey("m") || !argumentsMap.containsKey("t") || !argumentsMap.containsKey("u")) {
            throw new DukeException(Messages.EXCEPTION_INVALID_ARGUMENTS);
        }
        String module = argumentsMap.get("m");
        String type = argumentsMap.get("t");
        String url = argumentsMap.get("u");
        Link newLink = new Link(module, type, url);
        links.addLink(newLink);
    }

    private void executeAddModule(ModuleList modules) throws DukeException {
        int mc;

        if (!argumentsMap.containsKey("g") || !argumentsMap.containsKey("mc") || !argumentsMap.containsKey("ay")) {
            throw new DukeException("OOPS!!! g, mc and ay arguments are required!");
        }

        try {
            mc = Integer.parseInt(argumentsMap.get("mc"));
        } catch (NumberFormatException e) {
            throw new DukeException("OOPS!!! Your MCs are invalid!");
        }

        Module module = new Module(description, argumentsMap.get("g"), mc, argumentsMap.get("ay"));
        modules.addTask(module);
    }

    /**
     * Sets the properties of a given Task.
     *
     * @param task Task to set the properties of.
     * @param argumentsMap HashMap containing arguments to set the Task properties.
     * @throws DukeException If arguments in HashMap are invalid.
     */
    protected void setTaskProperties(Task task, HashMap<String, String> argumentsMap) throws DukeException {
        if (argumentsMap.containsKey("p")) {
            int newPriority;
            try {
                newPriority = Integer.parseInt(argumentsMap.get("p"));
            } catch (NumberFormatException e) {
                throw new DukeException(Messages.EXCEPTION_INVALID_INDEX);
            }
            if (newPriority < 0) {
                throw new DukeException(Messages.EXCEPTION_INVALID_PRIORITY);
            }
            task.setPriority(newPriority);
        }

        if (argumentsMap.containsKey("c")) {
            if (argumentsMap.get("c") != null) {
                task.setCategory(argumentsMap.get("c"));
            }
        }

        if (argumentsMap.containsKey("date")) {
            task.setDateFromString(argumentsMap.get("date"));
        }
    }
}
