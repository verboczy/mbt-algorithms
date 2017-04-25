package bme.mit.algorithms;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bme.mit.graph.Edge;
import bme.mit.graph.Graph;
import bme.mit.graph.Node;

/**
 * It contains algorithms that traverse the graph randomly.
 * @author verboczy
 *
 */
public class RandomAlgorithm extends Algorithm {
	
	private Logger logger = LoggerFactory.getLogger(RandomAlgorithm.class);
	
	private StringBuilder pathBuilder;
	
	@Override
	public String getAllNodeVisited(Graph graph) {
		pathBuilder = new StringBuilder("");
		
		Node initNode = getRandomNode(graph);
		
		traverseNodes(graph, initNode);
		
		return pathBuilder.toString();
	}

	@Override
	public String getAllEdgeVisited(Graph graph) {
		pathBuilder = new StringBuilder("");
		
		Node initNode = getRandomNode(graph);
		
		traverseEdges(graph, initNode);
		
		return pathBuilder.toString();
	}

	@Override
	public String getAllNodeVisitedFromGivenNode(Graph graph, String init) {
		pathBuilder = new StringBuilder("");
		
		Node initial = getNodeByName(graph, init);
		traverseNodes(graph, initial);
		
		return pathBuilder.toString();
	}

	@Override
	public String getAllEdgeVisitedFromGivenNode(Graph graph, String init) {
		pathBuilder = new StringBuilder("");
		
		Node initial = getNodeByName(graph, init);
		traverseEdges(graph, initial);
		
		return pathBuilder.toString();
	}
	
	/**
	 * Visits the nodes from an initial node, but randomly.
	 */
	public void traverseNodes(Graph graph, Node initial) {
		
		//initial.printMyself();
		pathBuilder.append(initial.getName());
		pathBuilder.append(";");
		initial.setVisitedCount(initial.getVisitedCount() + 1);
		nodeSet.add(initial);
		
		Random rnd = new Random();
		int index = rnd.nextInt(initial.getEdges().size());		
		Edge edge = initial.getEdges().get(index);
		
		
		if (nodeSet.equals(graph.getNodes())) {
			//logger.info("all nodes have been visited.");
			super.reset(graph);
		}
		else {
			traverseNodes(graph, edge.getEndNode());
		}		
	}

	/**
	 * Visits the edges from an initial node, but randomly.
	 */
	public void traverseEdges(Graph graph, Node initial) {
		
		Random rnd = new Random();
		int index = rnd.nextInt(initial.getEdges().size());
		
		Edge edge = initial.getEdges().get(index);
		edgeSet.add(edge);
		//edge.printMyself();
		pathBuilder.append(edge.getInputLabel());
		pathBuilder.append(";");
		
		if (edgeSet.equals(graph.getEdges())) {
			//logger.info("all edges have been visited. (randomAlgo)");
			super.reset(graph);
		}
		else {
			traverseEdges(graph, edge.getEndNode());
		}		
	}

}
