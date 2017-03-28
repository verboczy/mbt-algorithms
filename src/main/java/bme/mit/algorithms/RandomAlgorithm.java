package bme.mit.algorithms;

import java.util.Random;

import bme.mit.graph.Edge;
import bme.mit.graph.Graph;
import bme.mit.graph.Node;
import bme.mit.helper.Logger;

/**
 * It contains algorithms that traverse the graph randomly.
 * @author verboczy
 *
 */
public class RandomAlgorithm extends Algorithm {

	@Override
	public void traverse(Graph graph)  {
		Logger.getInstance().logError("RandomAlgorithm.java: traverse(Graph graph) - unimplemented method.");
	}

	@Override
	public void traverse(Graph graph, Node initial) {
		Logger.getInstance().logError("RandomAlgorithm.java: traverse(Graph graph, Node initial) - unimplemented method.");
	}
	
	/**
	 * Visits the nodes from an initial node, but randomly.
	 */
	@Override
	public void traverseNodes(Graph graph, Node initial) {
		
		initial.printMyself();
		initial.setVisitedCount(initial.getVisitedCount() + 1);
		nodeSet.add(initial);
		
		Random rnd = new Random();
		int index = rnd.nextInt(initial.getEdges().size());		
		Edge edge = initial.getEdges().get(index);
		
		
		if (nodeSet.equals(graph.getNodes())) {
			Logger.getInstance().logMessage("RandomAlgoritm.java: All nodes have been visited.");
			super.reset(graph);
		}
		else {
			traverseNodes(graph, edge.getEndNode());
		}		
	}

	/**
	 * Visits the edges from an initial node, but randomly.
	 */
	@Override
	public void traverseEdges(Graph graph, Node initial) {
		
		Random rnd = new Random();
		int index = rnd.nextInt(initial.getEdges().size());
		
		Edge edge = initial.getEdges().get(index);
		edgeSet.add(edge);
		edge.printMyself();
		
		if (edgeSet.equals(graph.getEdges())) {
			Logger.getInstance().logMessage("RandomAlgorithm.java: All edges have been visited. (randomAlgo)");
			super.reset(graph);
		}
		else {
			traverseEdges(graph, edge.getEndNode());
		}		
	}

}
