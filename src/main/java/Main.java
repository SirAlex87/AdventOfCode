
import com.google.ortools.Loader;
import com.google.ortools.linearsolver.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	
	private static Logger logger = LogManager.getLogger(Main.class);

public static void main(String[] args) {
        Loader.loadNativeLibraries();

        int[][] buttons = {
            {0,2,3,5,7,8},
            {1,2,8},
            {2,6,8},
            {0,2,4,6,7,8},
            {0,1,8},
            {0,4,5,8},
            {1,3},
            {0,1,3,4,8},
            {0,2,5,8},
            {5},
            {0,1,2,3,5,7}
        };

        int[] target = {77,51,54,27,31,69,6,17,94};

        MPSolver solver =
            MPSolver.createSolver("CBC"); // risolutore intero

        MPVariable[] x = new MPVariable[buttons.length];

        // Variabili: x[i] >= 0, intero
        MPSolver.ResultStatus result = resoveFunction(buttons, target, solver, x);

        if (result == MPSolver.ResultStatus.OPTIMAL) {
        	logger.info("Soluzione ottima:");
            int total = 0;
            for (int i = 0; i < x.length; i++) {
                int val = (int) x[i].solutionValue();
                total += val;
                logger.log(Level.INFO,"Bottone {0}:{1}",i,val);
            }
            logger.log(Level.INFO,"Totale pressioni = {0}",total);
        } else {
        	logger.info("Nessuna soluzione trovata");
        }
    }

private static MPSolver.ResultStatus resoveFunction(int[][] buttons, int[] target, MPSolver solver, MPVariable[] x) {
	for (int i = 0; i < x.length; i++) {
	    x[i] = solver.makeIntVar(0, Double.POSITIVE_INFINITY, "x" + i);
	}

	// Vincoli sui 9 contatori
	for (int j = 0; j < target.length; j++) {
	    MPConstraint c = solver.makeConstraint(target[j], target[j]);

	    for (int i = 0; i < buttons.length; i++) {
	        for (int idx : buttons[i]) {
	            if (idx == j) {
	                c.setCoefficient(x[i], 1);
	                break;
	            }
	        }
	    }
	}

	// Funzione obiettivo: minimizzare il numero di pressioni
	MPObjective obj = solver.objective();
	for (MPVariable v : x) {
	    obj.setCoefficient(v, 1);
	}
	obj.setMinimization();

	// Risolvi
	return solver.solve();
	
}


}
