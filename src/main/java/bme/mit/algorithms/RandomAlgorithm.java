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
	
	Logger log = LoggerFactory.getLogger(RandomAlgorithm.class);

	@Override
	public void traverse(Graph graph)  {
		log.debug("traverse(Graph graph) - unimplemented method.");
	}

	@Override
	public void traverse(Graph graph, Node initial) {
		log.debug("traverse(Graph graph, Node initial) - unimplemented method.");
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
			log.info("all nodes have been visited.");
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
			log.info("all edges have been visited. (randomAlgo)");
			super.reset(graph);
		}
		else {
			traverseEdges(graph, edge.getEndNode());
		}		
	}

}
