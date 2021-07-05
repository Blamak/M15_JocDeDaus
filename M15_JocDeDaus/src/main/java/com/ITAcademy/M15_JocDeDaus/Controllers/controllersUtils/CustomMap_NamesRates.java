package com.ITAcademy.M15_JocDeDaus.Controllers.controllersUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Class to create a list of maps name-rate 
 * that allow duplicate key names ("ANONIM" case)
 */
public class CustomMap_NamesRates {

	private List<String> names = new ArrayList<String>();
	private List<Double> winRates = new ArrayList<Double>();
	private int numberOfPlayers = 0;
	
	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<Double> getWinRates() {
		return winRates;
	}

	public void setWinRates(List<Double> winRates) {
		this.winRates = winRates;
	}

	public CustomMap_NamesRates(List<String> names, List<Double> winRates, int numberOfPlayers) {
		super();
		this.names = names;
		this.winRates = winRates;
		this.numberOfPlayers = numberOfPlayers;
	}

	public CustomMap_NamesRates() {

	}

	public void addNameAndRate(String name, double winRate) {
		this.names.add(name);
		this.winRates.add(winRate);
		this.numberOfPlayers++;
		this.sortByRate();
	}

	@SuppressWarnings("unchecked")
	public List<HashMap<String, Double>> showCustomMap() {
		int index = 0;
		HashMap<String, Double> nameRatePair = new HashMap<>();
		List<HashMap<String, Double>> playersList = new ArrayList<>();
		for (String name : this.names) {
			nameRatePair.put(name, this.winRates.get(index));
			playersList.add((HashMap<String, Double>) nameRatePair.clone());
			nameRatePair.clear();
			index++;
		}
		return playersList;
	}

	public void sortByRate() {
		
		if (this.numberOfPlayers > 1) {	
			
			for (int i = this.numberOfPlayers-1; i > 0; i--) {
				
				if (this.winRates.get(i).compareTo(this.winRates.get(i-1)) == 1) {
					this.winRates.add(i-1, this.winRates.remove(i));
					this.names.add(i-1, this.names.remove(i));
				} else {
					break;
				}
			}

		}
	}

}
