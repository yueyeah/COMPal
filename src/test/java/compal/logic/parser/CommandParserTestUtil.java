package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.CommandResult;
import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.TaskList;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author SholihinK
public class CommandParserTestUtil {

    /**
     * Asserts that the parsing of user input by  parserManager is successful and the command created
     * equals to expectedCommand.
     */
    public static void assertParseSuccess(CommandParser testCommandParser, String userInput,
                                   CommandResult expectedCommandResult, TaskList taskList) {
        try {
            Command testCommand = testCommandParser.parseCommand(userInput);
            CommandResult testCommandResult = testCommand.commandExecute(taskList);
            assertEquals(expectedCommandResult.feedbackToUser, testCommandResult.feedbackToUser);
        } catch (ParserException | CommandException | ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Asserts that the parsing of user input by parser is unsuccessful and the error message
     * equals to the errorMessage.
     */
    public static void assertParseFailure(CommandParser parserManager, String userInput, String expectedMessage) {
        try {
            parserManager.parseCommand(userInput);
            throw new AssertionError("ParserException was not thrown.");
        } catch (ParserException | ParseException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}