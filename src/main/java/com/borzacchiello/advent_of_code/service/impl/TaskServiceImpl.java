package com.borzacchiello.advent_of_code.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.borzacchiello.advent_of_code.dto.response.TaskResult;
import com.borzacchiello.advent_of_code.exception.InvalidFileException;
import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.exception.TaskNotFoundException;
import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.parser.TaskParser;
import com.borzacchiello.advent_of_code.service.FileValidator;
import com.borzacchiello.advent_of_code.service.TaskService;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;
import com.borzacchiello.advent_of_code.strategy.TaskStrategyFactory;

/**
 * Implementazione del servizio applicativo per l'esecuzione dei task.
 *
 * <p>Il servizio rappresenta il livello di orchestrazione tra il
 * controller REST e le strategie algoritmiche.</p>
 *
 * <p>La classe è indipendente dai singoli task: non contiene
 * riferimenti a Task01, Task02 o ai relativi modelli. La strategia
 * corretta viene selezionata dinamicamente tramite
 * {@link TaskStrategyFactory} utilizzando la coppia Task/Parte.</p>
 *
 * <p>Il flusso di elaborazione è:</p>
 *
 * <ol>
 *   <li>validazione del file ricevuto;</li>
 *   <li>lettura delle righe del file;</li>
 *   <li>costruzione della {@link TaskKey};</li>
 *   <li>selezione della strategia;</li>
 *   <li>esecuzione dell'algoritmo;</li>
 *   <li>costruzione del risultato.</li>
 * </ol>
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final FileValidator fileValidator;
    private final Map<Integer, TaskParser<?>> parsers;
    private final TaskStrategyFactory strategyFactory;

    /**
     * Costruisce il servizio con le dipendenze necessarie.
     *
     * <p>Spring utilizza la constructor injection per fornire
     * automaticamente il validatore del file e la factory delle
     * strategie.</p>
     *
     * @param fileValidator componente responsabile della validazione
     *        del file ricevuto
     * @param strategyFactory factory utilizzata per selezionare
     *        la strategia richiesta
     */
    public TaskServiceImpl(
            FileValidator fileValidator,
            List<TaskParser<?>> parsers,
            TaskStrategyFactory strategyFactory) {

        this.fileValidator = fileValidator;
        this.parsers = parsers.stream()
                .collect(
                        Collectors.toUnmodifiableMap(
                        		TaskParser::getTaskNumber,
                                Function.identity()
                        )
                );
        this.strategyFactory = strategyFactory;
    }

    /**
     * Esegue il task e la parte richiesti utilizzando il file
     * fornito dal client.
     *
     * <p>Il metodo non contiene logica specifica di alcun task.
     * Dopo la validazione e la lettura del file, individua la
     * strategia appropriata tramite {@link TaskStrategyFactory}
     * e delega completamente l'elaborazione alla Strategy.</p>
     *
     * @param task numero del task da eseguire
     * @param part numero della parte da eseguire
     * @param file file TXT contenente l'input
     * @return risultato dell'elaborazione
     * @throws InvalidFileException se il file non è valido
     *         o non può essere letto
     * @throws TaskNotFoundException se la combinazione Task/Parte
     *         richiesta non è disponibile
     */
    @Override
    public TaskResult execute(
            int task,
            int part,
            MultipartFile file)
            throws InvalidFileException, TaskNotFoundException, InvalidTaskInputException {

        fileValidator.validate(file);

        List<String> lines = readFile(file);

        Object parsedInput = parseInput(task, lines);

        TaskKey key = new TaskKey(task, part);

        TaskStrategy<?, ?> strategy =
                strategyFactory.getStrategy(key);

        Object result =
                executeStrategy(strategy, parsedInput);

        return new TaskResult(
                task,
                part,
                result
        );
    }

    /**
     * Seleziona il parser da utilizzare per il task richiesto.
     *
     * <p>Per il momento è disponibile solamente il Task 1.
     * L'input testuale viene convertito in una lista di rotazioni.</p>
     *
     * @param task numero del task
     * @param lines righe lette dal file
     * @return input convertito nel modello richiesto dal task
     * @throws TaskNotFoundException 
     * @throws InvalidTaskInputException 
     */
    private Object parseInput(
            int task,
            List<String> lines) throws TaskNotFoundException, InvalidTaskInputException {

    	TaskParser<?> parser = parsers.get(task);
    	if (parser == null) {
            throw new TaskNotFoundException(
                    "Task non trovato: " + task
            );
        }
        return parser.parse(lines);
    }

    /**
     * Esegue una strategia utilizzando l'input precedentemente
     * convertito dal parser.
     *
     * <p>Il cast è confinato in questo metodo perché la Factory
     * contiene Strategy con tipi di input e output differenti.</p>
     *
     * @param strategy strategia selezionata dalla Factory
     * @param input input convertito per il task richiesto
     * @return risultato prodotto dalla strategia
     */
    private <I, O> O executeStrategy(
            TaskStrategy<?, ?> strategy,
            Object input) {

        TaskStrategy<I, O> typedStrategy =
                (TaskStrategy<I, O>) strategy;

        return typedStrategy.execute((I) input);
    }

    /**
     * Legge il contenuto del MultipartFile come sequenza di righe UTF-8.
     *
     * @param file file ricevuto dalla richiesta HTTP
     * @return righe contenute nel file
     * @throws InvalidFileException se il file non è leggibile
     */
    private List<String> readFile(
            MultipartFile file)
            throws InvalidFileException {

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     file.getInputStream(),
                                     StandardCharsets.UTF_8))) {

            return reader.lines().toList();

        } catch (IOException exception) {

            throw new InvalidFileException(
                    "Errore durante la lettura del file",
                    exception
            );
        }
    }
}