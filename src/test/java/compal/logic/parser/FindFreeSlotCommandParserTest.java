package compal.logic.parser;

import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static compal.logic.parser.CommandParserTestUtil.assertParseFailure;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Catherinetan99
public class FindFreeSlotCommandParserTest {
    private FindFreeSlotCommandParser parser = new FindFreeSlotCommandParser();
    private ArrayList<Task> taskArrList = new ArrayList<>();
    private TaskList taskList = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        taskList.setArrList(taskArrList);
        Event event1 = new Event("Event 1", Task.Priority.medium, "05/12/2020", "05/12/2020", "0000", "0800");
        taskList.addTask(event1);

        Event event2 = new Event("Event 2", Task.Priority.high, "05/12/2020", "05/12/2020", "0900", "1600");
        taskList.addTask(event2);

        Event event3 = new Event("Event 3", Task.Priority.low, "05/12/2020", "05/12/2020", "1000", "1200");
        taskList.addTask(event3);
    }


    @Test
    void parser_missingDateToken_exceptionThrown() {
        assertParseFailure(parser, "", "ArgumentError: Missing /date");
    }

    @Test
    void parser_missingHourToken_exceptionThrown() {
        assertParseFailure(parser, "/date 10/12/2020", "ArgumentError: Missing /hour");
    }

    @Test
    void parser_missingMinToken_exceptionThrown() {
        assertParseFailure(parser, "/date 10/12/2020 /hour 5", "ArgumentError: Missing /min");
    }

    @Test
    void parser_missingDateInput_exceptionThrown() {
        assertParseFailure(parser, "/date", "Error: Missing input!");
    }

    @Test
    void parser_missingHourInput_exceptionThrown() {
        assertParseFailure(parser, "/date 26/12/2020 /hour", "Error: Missing input!");
    }

    @Test
    void parser_missingMinInput_exceptionThrown() {
        assertParseFailure(parser, "/date 26/12/2020 /hour 5 /min", "Error: Missing input!");
    }

    @Test
    void parser_invalidDateInput_exceptionThrown() {
        assertParseFailure(parser, "/date 26-10-19", "Invalid Date input!");
    }

    @Test
    void parser_invalidHourInput_exceptionThrown() {
        assertParseFailure(parser, "/date 26/12/2020 /hour invalid", "Invalid hour input!");
    }

    @Test
    void parser_invalidMinInput_exceptionThrown() {
        assertParseFailure(parser, "/date 26/12/2020 /hour 1 /min abc", "Invalid min input!");
    }

    @Test
    void parser_exceededHourInput_exceptionThrown() {
        assertParseFailure(parser, "/date 26/12/2020 /hour 234567897891 /min 30",
                "Error: Input entered is out of range!");
    }

    @Test
    void parser_exceededMinInput_exceptionThrown() {
        assertParseFailure(parser, "/date 26/12/2020 /hour 1 /min 123456789045",
                "Error: Input entered is out of range!");
    }

    @Test
    void parser_pastDateInput_exceptionThrown() {
        assertParseFailure(parser, "/date 26/10/2019 /hour 1 /min 1",
                "Error: Date entered cannot be a past date!");
    }

    @Test
    void parser_findFreeSlotParser_success() throws ParseException, ParserException, CommandException {
        String testInput = "/date 05/12/2020 /hour 1 /min 0";
        StringBuilder freeSlot = new StringBuilder("Here are the available time slots for 05/12/2020:\n");
        freeSlot.append("1. 0800 to 0900\n");
        freeSlot.append("2. 1600 to 2400\n");
        String result = freeSlot.toString();
        assertEquals(result, parser.parseCommand(testInput).commandExecute(taskList).feedbackToUser);
    }

    @Test
    void parser_findFreeSlotParser_noSlotsFound() throws ParseException, ParserException, CommandException {
        String testInput = "/date 05/12/2020 /hour 10 /min 0";
        String freeSlot = "You have no available slots on 05/12/2020 ! :(";
        assertEquals(freeSlot, parser.parseCommand(testInput).commandExecute(taskList).feedbackToUser);
    }

    @Test
    void parser_findFreeSlotParser_noTasksOnInputDate() throws ParseException, ParserException, CommandException {
        String testInput = "/date 06/12/2020 /hour 1 /min 30";
        StringBuilder freeSlot = new StringBuilder("Here are the available time slots for 06/12/2020:\n");
        freeSlot.append("1. 0000 to 2400\n");
        String result = freeSlot.toString();
        assertEquals(result, parser.parseCommand(testInput).commandExecute(taskList).feedbackToUser);
    }

}
