package org.jgrapht.demo;

import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;

public class heuristicoPrueba implements AStarAdmissibleHeuristic<String> {

	@Override
	public double getCostEstimate(String arg0, String arg1) {
		return 1;
	}

}
