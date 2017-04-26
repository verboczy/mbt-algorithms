package bme.mit.algorithms;

import java.util.Random;

import bme.mit.graph.Edge;
import bme.mit.graph.Graph;
import bme.mit.graph.Node;

/**
 * It contains algorithms that traverse the graph randomly.
 * @author verboczy
 *
 */
public class RandomAlgorithm extends Algorithm {
		
	private StringBuilder pathBuilder;
	
	@Override
	public String getAllNodeVisited(Graph graph) {
		pathBuilder = new StringBuilder("");
		
		Node initNode = getRandomNode(graph);
		stepCount = 0;
		
		traverseNodes(graph, initNode);
		
		return pathBuilder.toString();
	}

	@Override
	public String getAllEdgeVisited(Graph graph) {
		pathBuilder = new StringBuilder("");
		
		Node initNode = getRandomNode(graph);
		pathBuilder.append(initNode.getName());
		pathBuilder.append(";");
		
		stepCount = 0;
		
		traverseEdges(graph, initNode);
		
		return pathBuilder.toString();
	}

	@Override
	public String getAllNodeVisitedFromGivenNode(Graph graph, String init) {
		pathBuilder = new StringBuilder("");
		
		Node initial = getNodeByName(graph, init);
		stepCount = 0;
		traverseNodes(graph, initial);
		
		return pathBuilder.toString();
	}

	@Override
	public String getAllEdgeVisitedFromGivenNode(Graph graph, String init) {
		pathBuilder = new StringBuilder("");
		pathBuilder.append(init);
		pathBuilder.append(";");
		
		Node initial = getNodeByName(graph, init);
		stepCount = 0;
		traverseEdges(graph, initial);
		
		return pathBuilder.toString();
	}
	
	/**
	 * Visits the nodes from an initial node, but randomly.
	 */
	public void traverseNodes(Graph graph, Node initial) {
		
		pathBuilder.append(initial.getName());
		pathBuilder.append(";");
		initial.setVisitedCount(initial.getVisitedCount() + 1);
		nodeSet.add(initial);
		
		Random rnd = new Random();
		int outgoingEdgeCount = initial.getEdges().size();
		if (outgoingEdgeCount == 0 || stepCount >= TIMEOUT) {
			return;
		}
		int index = rnd.nextInt(outgoingEdgeCount);		
		Edge edge = initial.getEdges().get(index);
		
		
		if (nodeSet.equals(graph.getNodes())) {
			super.reset(graph);
		}
		else {
			stepCount++;
			traverseNodes(graph, edge.getEndNode());
		}		
	}

	/**
	 * Visits the edges from an initial node, but randomly.
	 */
	public void traverseEdges(Graph graph, Node initial) {
		
		Random rnd = new Random();
		int outgoingEdgeCount = initial.getEdges().size();
		if (outgoingEdgeCount == 0 || stepCount >= TIMEOUT) {
			return;
		}
		int index = rnd.nextInt(outgoingEdgeCount);
		
		Edge edge = initial.getEdges().get(index);
		edgeSet.add(edge);
		pathBuilder.append(edge.getInputLabel());
		pathBuilder.append(";");
		
		if (edgeSet.equals(graph.getEdges())) {
			super.reset(graph);
		}
		else {
			stepCount++;
			traverseEdges(graph, edge.getEndNode());
		}		
	}

}
