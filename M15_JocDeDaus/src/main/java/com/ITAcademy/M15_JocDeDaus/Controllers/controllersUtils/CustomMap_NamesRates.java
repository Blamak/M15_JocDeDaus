package com.ITAcademy.M15_JocDeDaus.Controllers.controllersUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomMap_NamesRates {

	private List<String> names = new ArrayList<String>();
	private List<BigDecimal> winRates = new ArrayList<BigDecimal>();
	private int players = 0;
	
	

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<BigDecimal> getWinRates() {
		return winRates;
	}

	public void setWinRates(List<BigDecimal> winRates) {
		this.winRates = winRates;
	}

	public CustomMap_NamesRates(List<String> names, List<BigDecimal> winRates, int players) {
		super();
		this.names = names;
		this.winRates = winRates;
		this.players = players;
	}

	public CustomMap_NamesRates() {
		// TODO Auto-generated constructor stub
	}

	public void addNameAndRate(String name, BigDecimal winRate) {
		this.names.add(name);
		this.winRates.add(winRate);
		this.players++;
		this.sortByRate();
	}

	public int getPlayers() {
		return players;
	}

	@SuppressWarnings("unchecked")
	public List<HashMap<String, BigDecimal>> showCustomMap() {
		int index = 0;
		HashMap<String, BigDecimal> nameRatePair = new HashMap<>();
		List<HashMap<String, BigDecimal>> playersList = new ArrayList<>();
		for (String name : this.names) {
			nameRatePair.put(name, this.winRates.get(index));
			playersList.add((HashMap<String, BigDecimal>) nameRatePair.clone());
			nameRatePair.clear();
			index++;
		}
		return playersList;
	}

	public void sortByRate() {
		
		if (this.players > 1) {	
			
			for (int i = this.players-1; i > 0; i--) {
				
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
