package com.borzacchiello.advent_of_code.parser.task05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task05.IngredientData;
import com.borzacchiello.advent_of_code.model.task05.IngredientIdRange;
import com.borzacchiello.advent_of_code.parser.TaskParser;

@Component
public class Task05Parser
        implements TaskParser<IngredientData> {

    private static final int TASK_NUMBER = 5;

    @Override
    public int getTaskNumber() {
        return TASK_NUMBER;
    }

    /**
     * Trasforma le righe dell'input nel modello del Task 05.
     *
     * La prima sezione contiene gli intervalli freschi.
     * La seconda sezione contiene gli ID disponibili.
     * Le sezioni sono separate dalla prima riga vuota.
     *
     * @param lines righe dell'input
     * @return database degli ingredienti
     */
    @Override
    public IngredientData parse(
            List<String> lines) {

        if (lines == null || lines.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 5 input is empty");
        }

        List<IngredientIdRange> freshRanges =
                new ArrayList<>();

        List<BigInteger> availableIds =
                new ArrayList<>();

        boolean availableSection = false;
        boolean separatorFound = false;

        for (String line : lines) {

            if (line == null || line.isBlank()) {
                availableSection = true;
                separatorFound = true;
                continue;
            }

            String value = line.strip();

            if (availableSection) {
                availableIds.add(
                        parseAvailableId(value));
            } else {
                freshRanges.add(
                        parseRange(value));
            }
        }

        validateInput(
                freshRanges,
                separatorFound);

        return new IngredientData(
                freshRanges,
                availableIds);
    }

    /**
     * Converte un intervallo nel formato start-end.
     */
    private IngredientIdRange parseRange(
            String value) {

        String[] parts = value.split("-", -1);

        if (parts.length != 2
                || parts[0].isBlank()
                || parts[1].isBlank()) {

            throw new InvalidTaskInputException(
                    "Invalid fresh ingredient range: "
                            + value);
        }

        try {
            BigInteger start =
                    new BigInteger(parts[0].strip());

            BigInteger end =
                    new BigInteger(parts[1].strip());

            if (start.compareTo(end) > 0) {
                throw new InvalidTaskInputException(
                        "Invalid fresh ingredient range: "
                                + "start is greater than end");
            }

            return new IngredientIdRange(
                    start,
                    end);

        } catch (NumberFormatException exception) {
            throw new InvalidTaskInputException(
                    "Invalid fresh ingredient range: "
                            + value);
        }
    }

    /**
     * Converte una riga della seconda sezione in un ID.
     */
    private BigInteger parseAvailableId(
            String value) {

        try {
            return new BigInteger(value);
        } catch (NumberFormatException exception) {
            throw new InvalidTaskInputException(
                    "Invalid available ingredient ID: "
                            + value);
        }
    }

    /**
     * Verifica che siano presenti gli intervalli e il separatore
     * tra le due sezioni.
     */
    private void validateInput(
            List<IngredientIdRange> freshRanges,
            boolean separatorFound) {

        if (freshRanges.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 5 contains no fresh ranges");
        }

        if (!separatorFound) {
            throw new InvalidTaskInputException(
                    "Task 5 sections must be separated "
                            + "by a blank line");
        }
    }
}
