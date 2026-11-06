package com.borzacchiello.advent_of_code.parser.task01;

import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task01.Direction;
import com.borzacchiello.advent_of_code.model.task01.Rotation;
import com.borzacchiello.advent_of_code.parser.TaskParser;

/**
 * Parser specifico per il Task 1.
 *
 * <p>Il componente converte le rappresentazioni testuali delle
 * rotazioni nel modello applicativo {@link Rotation}.</p>
 *
 * <p>Una riga valida è costituita da una direzione, rappresentata
 * dai caratteri L oppure R, seguita da una distanza numerica.</p>
 *
 * <p>Esempi:</p>
 *
 * <pre>
 * L68 -> Rotation(LEFT, 68)
 * R48 -> Rotation(RIGHT, 48)
 * </pre>
 *
 * <p>Il parser non contiene la logica di risoluzione del puzzle.
 * La sua unica responsabilità è validare e trasformare l'input
 * testuale.</p>
 */
@Component
public class Task01Parser
        implements TaskParser<List<Rotation>> {

    private static final int TASK_NUMBER = 1;
    private static final String ROTATION_PATTERN = "^[LR]\\d+$";

    @Override
    public int getTaskNumber() {
        return TASK_NUMBER;
    }

    /**
     * Converte una lista di righe testuali in una lista di rotazioni.
     *
     * <p>Ogni riga viene elaborata individualmente tramite
     * {@link #parseRotation(String)}.</p>
     *
     * @param lines righe lette dal file di input
     * @return lista ordinata delle rotazioni
     * @throws IllegalArgumentException se la lista è nulla
     * @throws InvalidTaskInputException se almeno una riga non
     *         rispetta il formato previsto
     */
    @Override
    public List<Rotation> parse(List<String> lines){

        if (lines == null) {
            throw new IllegalArgumentException(
                    "Input lines cannot be null"
            );
        }

        return lines.stream()
                .map(this::parseRotation)
                .toList();		
    }

    /**
     * Converte una singola riga nella corrispondente rotazione.
     *
     * <p>Il primo carattere determina la direzione, mentre i
     * caratteri successivi rappresentano la distanza.</p>
     *
     * @param line riga da convertire
     * @return rotazione risultante
     * @throws InvalidTaskInputException se la riga è nulla,
     *         malformata o contiene una distanza non valida
     */
    private Rotation parseRotation(String line) throws InvalidTaskInputException {

        if (line == null || !line.matches(ROTATION_PATTERN)) {
            throw new InvalidTaskInputException(
                    "Invalid rotation: " + line
            );
        }

        Direction direction = switch (line.charAt(0)) {
            case 'L' -> Direction.LEFT;
            case 'R' -> Direction.RIGHT;
            default -> throw new InvalidTaskInputException(
                    "Invalid direction: " + line.charAt(0)
            );
        };

        int distance;

        try {
            distance = Integer.parseInt(line.substring(1));
        } catch (NumberFormatException exception) {
            throw new InvalidTaskInputException(
                    "Invalid distance in rotation: " + line,
                    exception
            );
        }

        return new Rotation(direction, distance);
    }
}