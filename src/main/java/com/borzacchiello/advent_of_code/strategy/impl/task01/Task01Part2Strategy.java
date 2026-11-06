package com.borzacchiello.advent_of_code.strategy.impl.task01;

import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task01.Direction;
import com.borzacchiello.advent_of_code.model.task01.Rotation;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

/**
 * Strategia di risoluzione del Task 1 - Parte 2.
 *
 * <p>A differenza della Parte 1, questa strategia conta ogni
 * passaggio della manopola sulla posizione zero, indipendentemente
 * dal fatto che tale posizione venga raggiunta durante oppure
 * al termine della rotazione.</p>
 *
 * <p>L'algoritmo evita di simulare ogni singolo click. Le rotazioni
 * superiori alla dimensione della manopola vengono suddivise in
 * giri completi e distanza residua:</p>
 *
 * <pre>
 * completeTurns = distance / 100
 * remainingDistance = distance % 100
 * </pre>
 *
 * <p>Questo consente di elaborare anche distanze elevate senza
 * effettuare un numero di iterazioni proporzionale alla distanza.</p>
 *
 * <p>La strategia è identificata dalla coppia Task 1 - Parte 2
 * e viene registrata automaticamente nella
 * {@link TaskStrategyFactory}.</p>
 */
@Component
public class Task01Part2Strategy implements TaskStrategy<List<Rotation>, Integer> {

    private static final int TASK_NUMBER = 1;
    private static final int PART_NUMBER = 2;

    private static final int DIAL_START_POSITION = 50;
    private static final int DIAL_SIZE = 100;

    @Override
    public TaskKey getKey() {
        return new TaskKey(TASK_NUMBER, PART_NUMBER);
    }

    /**
     * Esegue l'algoritmo previsto dal Task 1 - Parte 2.
     *
     * <p>Le righe ricevute vengono convertite in {@link Rotation}
     * tramite {@link Task01Parser}. Per ogni rotazione vengono
     * conteggiati sia i passaggi sulla posizione zero derivanti da
     * eventuali giri completi, sia l'eventuale passaggio prodotto
     * dalla distanza residua.</p>
     *
     * <p>Ad esempio, partendo dalla posizione 50, una rotazione
     * R1000 attraversa la posizione zero dieci volte.</p>
     *
     * @param input righe del file contenenti le rotazioni
     * @return numero complessivo di volte in cui un click porta
     *         la manopola sulla posizione zero
     * @throws IllegalArgumentException se l'input è nullo
     * @throws InvalidTaskInputException se una rotazione è malformata
     */
    @Override
    public Integer execute(List<Rotation> rotations) {
    	
    	if (rotations == null) {
            throw new IllegalArgumentException(
                    "The rotations list cannot be null"
            );
        }

        int currentPosition = DIAL_START_POSITION;
        int zeroCount = 0;

        for (Rotation rotation : rotations) {

            int distance = rotation.distance();

            int completeTurns = distance / DIAL_SIZE;
            int remainingDistance = distance % DIAL_SIZE;

            zeroCount += completeTurns;

            int nextPosition;

            if (rotation.direction() == Direction.LEFT) {

                int rawPosition =
                        currentPosition - remainingDistance;

                if (rawPosition <= 0 && currentPosition != 0) {
                    zeroCount++;
                }

                nextPosition =
                        Math.floorMod(rawPosition, DIAL_SIZE);

            } else {

                int rawPosition =
                        currentPosition + remainingDistance;

                if (rawPosition >= DIAL_SIZE &&
                        currentPosition != 0) {

                    zeroCount++;
                }

                nextPosition =
                        Math.floorMod(rawPosition, DIAL_SIZE);
            }

            currentPosition = nextPosition;
        }

        return zeroCount;
    }
}
