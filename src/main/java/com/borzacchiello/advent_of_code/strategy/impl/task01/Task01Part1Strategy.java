package com.borzacchiello.advent_of_code.strategy.impl.task01;

import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task01.Rotation;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

/**
 * Strategia di risoluzione del Task 1 - Parte 1.
 *
 * <p>
 * L'algoritmo simula una manopola circolare composta da 100
 * posizioni, numerate da 0 a 99. La posizione iniziale è 50.
 * </p>
 *
 * <p>
 * Per ogni rotazione viene calcolata la nuova posizione della
 * manopola mediante aritmetica modulare. Il risultato restituito
 * corrisponde al numero di rotazioni al termine delle quali la
 * manopola si trova esattamente sulla posizione 0.
 * </p>
 *
 * <p>
 * La classe implementa lo Strategy Pattern e rappresenta
 * l'algoritmo associato al Task 1 - Parte 1.
 * </p>
 *
 * <p>
 * La Strategy è stateless: tutte le variabili necessarie
 * all'elaborazione sono locali al metodo execute().
 * Questo consente al componente di essere utilizzato in sicurezza
 * come singleton gestito da Spring.
 * </p>
 */
@Component
public class Task01Part1Strategy
        implements TaskStrategy<List<Rotation>, Integer> {

    private static final int TASK_NUMBER = 1;
    private static final int PART_NUMBER = 1;

    private static final int DIAL_START_POSITION = 50;
    private static final int DIAL_SIZE = 100;
    private static final int TARGET_POSITION = 0;

    /**
     * Restituisce la chiave che identifica questa strategia.
     *
     * @return TaskKey corrispondente al Task 1 - Parte 1
     */
    @Override
    public TaskKey getKey() {
        return new TaskKey(TASK_NUMBER, PART_NUMBER);
    }

    /**
     * Esegue tutte le rotazioni e conta quante terminano
     * esattamente sulla posizione zero.
     *
     * @param rotations sequenza ordinata delle rotazioni validate
     * @return numero di rotazioni che terminano sulla posizione zero
     * @throws IllegalArgumentException se la lista delle rotazioni è null
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

            currentPosition = calculateNextPosition(
                    currentPosition,
                    rotation
            );

            if (currentPosition == TARGET_POSITION) {
                zeroCount++;
            }
        }

        return zeroCount;
    }

    /**
     * Calcola la nuova posizione della manopola applicando
     * una singola rotazione.
     *
     * <p>
     * Una rotazione LEFT produce una distanza negativa,
     * mentre una rotazione RIGHT produce una distanza positiva.
     * La nuova posizione viene normalizzata nell'intervallo
     * 0-99 tramite Math.floorMod().
     * </p>
     *
     * @param currentPosition posizione corrente della manopola
     * @param rotation rotazione da applicare
     * @return nuova posizione della manopola
     */
    private int calculateNextPosition(
            int currentPosition,
            Rotation rotation) {

        int signedDistance = switch (rotation.direction()) {
            case LEFT -> -rotation.distance();
            case RIGHT -> rotation.distance();
        };

        return Math.floorMod(
                currentPosition + signedDistance,
                DIAL_SIZE
        );
    }
}
