package bme.mit.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import bme.mit.graph.Edge;
import bme.mit.graph.Graph;
import bme.mit.graph.Node;

public class TestHelper {
	
	/**
	 * Checks if the given sequence is a homing sequence in the graph.
	 * @param graph
	 * @param homingSequence
	 * @return
	 */
	public boolean checkHomingSequence(Graph graph, String homingSequence) {
		
		Set<Node> nodes = graph.getNodes();
		
		char[] sequence = homingSequence.toCharArray();
		
		List<Node> lastNodes = new ArrayList<>();
		List<String> outputs = new ArrayList<>();
		
		int iter = 0;
		for (Node node : nodes) {
			Node lastNode = node;
			StringBuilder outputString = new StringBuilder("");
			for (int i = 0; i < sequence.length; i++) {
				StringBuilder sb = new StringBuilder("");
				sb.append(sequence[i]);
				String input = sb.toString();
				outputString.append(lastNode.getOutput(input));
				lastNode = lastNode.getNextNode(input);
			}
			String output = outputString.toString();
			outputs.add(output);
			lastNodes.add(lastNode);
			
			for (int j = 0; j < iter; j++) {
				// From the definition of homing sequence.
				if(!lastNode.getName().equals(lastNodes.get(j).getName()) && output.equals(outputs.get(j))) {
					return false;
				}				
			}
			
			iter++;
		}
		
		return true;
	}
	
	private Node getNodeByName(Graph graph, String name) {
		
		Iterator<Node> iter = graph.getNodes().iterator();
		Node node = null;
		
		while (iter.hasNext()) {
			node = iter.next();
			if (name.equals(node.getName())) {
				return node;
			}
		}
		
		return node;
	}
	
	// It is a valid path if all node is visited and all two consecutive nodes have an edge between themselves.
	public boolean checkNodePath(Graph graph, String path) {
		
		String[] elements = path.split(";");
		List<Node> nodeList = new ArrayList<>();
		
		// making a list of nodes from the array of node names
		for (int i = 0; i < elements.length; i++) {
			nodeList.add(getNodeByName(graph, elements[i]));
		}
		
		// checking the edges and increment the visited count
		int size = nodeList.size();
		for (int i = 0; i < size - 1; i++) {
			Node node = nodeList.get(i);
			int vc = node.getVisitedCount();
			node.setVisitedCount(++vc);
			
			
			Node nextNode = nodeList.get(i + 1);
			List<Edge> edges = node.getEdges();
			int index = 0;
			while (index < edges.size() && !nextNode.equals(edges.get(index).getEndNode())) {
				index++;
			}
			if (index >= edges.size()) {
				return false;
			}
		}
		int vc = nodeList.get(size - 1).getVisitedCount();
		nodeList.get(size - 1).setVisitedCount(++vc);
		
		// checking the visited count
		Set<Node> nodeSet = graph.getNodes();
		Iterator<Node> iter = nodeSet.iterator();
		while (iter.hasNext()) {
			Node node = iter.next();
			if (node.getVisitedCount() < 1) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean checkEdgePath(Graph graph, String path) {
		
		String[] elements = path.split(";");
		
		if (elements.length < 1) {
			return false;
		}
		
		Node node = getNodeByName(graph, elements[0]);
		if (node == null) {
			if (graph.getEdges().size() == 0) {
				return true;
			}
			return false;
		}
		
		for (int i = 1; i < elements.length; i++) {
			List<Edge> possibleEdges = node.getEdges();
			boolean foundEdge = false;
			for (Edge edge : possibleEdges) {
				if (elements[i].equals(edge.getInputLabel())) {
					node = edge.getEndNode();
					edge.setVisitedCount(edge.getVisitedCount() + 1);
					foundEdge = true;
					break;
				}
			}
			if (!foundEdge) {
				return false;
			}
		}
		
		Set<Edge> edges = graph.getEdges();
		
		
		Iterator<Edge> iter = edges.iterator();
		while (iter.hasNext()) {
			Edge edge = iter.next();
			if (edge.getVisitedCount() == 0) {
				return false;
			}
		}
			
		return true;
	}

}
