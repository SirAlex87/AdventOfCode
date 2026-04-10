package model;

import java.util.ArrayList;
import java.util.List;

public class MetricheStringheCoincidenti {
	
	private int coincidenti = 0;
	private List<Integer> posCoincidenti = new ArrayList<>();
	private List<Integer> posNonCoincidenti = new ArrayList<>();
	
	
	public MetricheStringheCoincidenti(int coincidenti, List<Integer> posCoincidenti, List<Integer> posNonCoincidenti) {
		super();
		this.coincidenti = coincidenti;
		this.posCoincidenti = posCoincidenti;
		this.posNonCoincidenti = posNonCoincidenti;
	}


	public int getCoincidenti() {
		return coincidenti;
	}


	public List<Integer> getPosCoincidenti() {
		return posCoincidenti;
	}


	public List<Integer> getPosNonCoincidenti() {
		return posNonCoincidenti;
	}


	public void setCoincidenti(int coincidenti) {
		this.coincidenti = coincidenti;
	}


	public void setPosCoincidenti(List<Integer> posCoincidenti) {
		this.posCoincidenti = posCoincidenti;
	}


	public void setPosNonCoincidenti(List<Integer> posNonCoincidenti) {
		this.posNonCoincidenti = posNonCoincidenti;
	}

}
