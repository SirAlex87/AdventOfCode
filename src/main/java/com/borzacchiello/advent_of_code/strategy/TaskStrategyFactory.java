package com.borzacchiello.advent_of_code.strategy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.TaskNotFoundException;
import com.borzacchiello.advent_of_code.model.TaskKey;

/**
 * Factory responsabile della registrazione e della selezione
 * delle strategie disponibili nell'applicazione.
 *
 * <p>Durante l'avvio dell'applicazione, Spring individua tutte le
 * classi annotate come componenti che implementano {@link TaskStrategy}
 * e le fornisce automaticamente al costruttore sotto forma di lista.</p>
 *
 * <p>La lista viene trasformata in una mappa indicizzata tramite
 * {@link TaskKey}. Questo consente di recuperare direttamente la
 * strategia associata a una coppia Task/Parte senza utilizzare
 * strutture condizionali come switch o catene di if/else.</p>
 *
 * <p>La classe svolge quindi il ruolo di Registry/Factory all'interno
 * dell'implementazione dello Strategy Pattern.</p>
 */
@Component
public class TaskStrategyFactory {

    private final Map<TaskKey, TaskStrategy<?, ?>> strategies;

    /**
     * Costruisce il registry delle strategie disponibili.
     *
     * <p>Spring esegue automaticamente la dependency injection della
     * lista, includendo tutti i bean che implementano
     * {@link TaskStrategy}.</p>
     * 
     * se un bean Spring ha un solo costruttore, Spring usa automaticamente quel costruttore per la
     *  Dependency Injection, anche senza @Autowired.
     *  
     *  Trovo TaskStrategyFactory
        ↓
		vedo un unico costruttore
        ↓
		il costruttore richiede List<TaskStrategy>
        ↓
		cerco tutti i bean TaskStrategy
        ↓
		costruisco la List
        ↓
		chiamo il costruttore
     *
     * <p>Ogni strategia viene registrata utilizzando come chiave il
     * valore restituito da {@link TaskStrategy#getKey()} e come valore
     * l'istanza stessa della strategia.</p>
     *
     * <p>La mappa risultante è immutabile. Due strategie con la stessa
     * {@link TaskKey} provocano un errore durante la costruzione della
     * mappa, evitando configurazioni ambigue.</p>
     *
     * @param strategies strategie individuate e istanziate da Spring
     */
    public TaskStrategyFactory(
            List<TaskStrategy<?, ?>> strategies) {

        this.strategies = strategies.stream()
                .collect(
                        Collectors.toUnmodifiableMap(
                                TaskStrategy::getKey,
                                Function.identity()
                        )
                );
    }

    /**
     * Recupera la strategia associata alla chiave specificata.
     *
     * @param key chiave contenente numero del task e numero della parte
     * @return strategia associata alla chiave
     * @throws IllegalArgumentException se la chiave è nulla
     * @throws TaskNotFoundException se non esiste una strategia
     *         associata alla chiave richiesta
     */
    @SuppressWarnings("unchecked")
    public <I, O> TaskStrategy<I, O> getStrategy(TaskKey key)
            throws TaskNotFoundException {

        if (key == null) {
            throw new IllegalArgumentException(
                    "Task key cannot be null"
            );
        }

        TaskStrategy<?, ?> strategy = strategies.get(key);

        if (strategy == null) {
            throw new TaskNotFoundException(
                    "Task not found: " + key
            );
        }

        return (TaskStrategy<I, O>) strategy;
    }
}