package bme.mit.sequences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import bme.mit.graph.Edge;
import bme.mit.graph.Graph;
import bme.mit.graph.Node;
import bme.mit.helper.HelperEdge;
import bme.mit.helper.HelperGraph;
import bme.mit.helper.HelperNode;

public class SynchronizingSequence implements Sequence {

	private HelperGraph correspondingMachine = new HelperGraph();
	private Set<String> inputs = new HashSet<>();

	/**
	 * Makes a set of the input symbols of the graph.
	 * @param graph
	 */
	private void setInputSet(Graph graph) {
		Set<Edge> edges = graph.getEdges();
		for (Edge edge : edges) {
			inputs.add(edge.getInputLabel());
		}
	}

	/**
	 * Transforms the original graph to a new graph. 
	 * We are able to find merging sequences on the new (transformed) graph.
	 * @param original
	 */
	public void computeCorrespondingMachine(Graph original) {
		setInputSet(original);
		List<Node> nodes = toArrayList(original.getNodes());

		// Add nodes to the graph
		for (int i = 0; i < nodes.size(); i++) {
			HelperNode elem = new HelperNode();
			elem.getNodes().add(nodes.get(i));
			correspondingMachine.getNodes().add(elem);
			for (int j = i + 1; j < nodes.size(); j++) {
				HelperNode elems = new HelperNode();
				elems.getNodes().add(nodes.get(i));
				elems.getNodes().add(nodes.get(j));
				correspondingMachine.getNodes().add(elems);
			}
		}

		// Add edges to the graph
		for (HelperNode node : correspondingMachine.getNodes()) {
			for (String input : inputs) {
				HelperNode next = new HelperNode();
				List<Node> nextNodes = new ArrayList<>();
				for (Node simpleNode : node.getNodes()) {
					if (!nextNodes.contains(simpleNode.getNextNode(input))) {
						nextNodes.add(simpleNode.getNextNode(input));
					}
				}
				next.setNodes(nextNodes);
				HelperEdge edge = new HelperEdge();
				edge.setInput(input);
				edge.setStartNode(node);
				edge.setEndNode(getNodeByElements(nextNodes));
				node.getEdges().add(edge);
				correspondingMachine.getEdges().add(edge);
			}
		}
	}

	/**
	 * Returns a helper node which contains the same nodes as the input list.
	 * @param paramNode
	 * @return
	 */
	private HelperNode getNodeByElements(List<Node> paramNode) {
		for (HelperNode nodeList : correspondingMachine.getNodes()) {
			if (nodeList.getNodes().size() == paramNode.size() && nodeList.getNodes().contains(paramNode.get(0))) {
				if (paramNode.size() == 2) {
					if (nodeList.getNodes().contains(paramNode.get(1))) {
						return nodeList;
					}
				} else {
					return nodeList;
				}
			}
		}

		return null;
	}

	/**
	 * Given a list of nodes and a sequence, it tells which nodes we will be in after applying the sequence to 
	 * the machine.
	 * @param inputStates
	 * @param seq
	 * @return
	 */
	private List<Node> delta(List<Node> inputStates, String seq) {
		
		List<Node> result = new ArrayList<>();

		String[] seqElems = seq.split(";");

		for (Node state : inputStates) {
			Node temp = state;
			for (int i = 1; i < seqElems.length; i++) {
				temp = temp.getNextNode(seqElems[i]);
			}
			if (!result.contains(temp)) {
				result.add(temp);				
			}
		}

		return result;
	}

	/**
	 * Converts a list to set.
	 * @param set
	 * @return
	 */
	private <T> List<T> toArrayList(Set<T> set) {

		List<T> result = new ArrayList<>();
		Iterator<T> iter = set.iterator();

		while (iter.hasNext()) {
			result.add(iter.next());
		}

		return result;
	}

	/**
	 * Synchronizing sequence.
	 */
	@Override
	public String getSequence(Graph graph) {

		computeCorrespondingMachine(graph);

		List<Node> nodes = toArrayList(graph.getNodes());

		StringBuilder x = new StringBuilder("");

		List<Node> finalStates = delta(nodes, x.toString());
		while (finalStates.size() > 1) {
			Node s = finalStates.get(0);
			Node t = finalStates.get(1);

			String y = mergingSequence(s, t);

			if ("-".equals(y)) {
				return y; // Failure
			}
			
			x.append(y);
			finalStates = delta(nodes, x.toString());
		}

		return x.toString();
	}

	/**
	 * Finds a merging sequence between two nodes. It is a BFS algorithm.
	 * @param s
	 * @param t
	 * @return
	 */
	private String mergingSequence(Node s, Node t) {
		clearGraph();
		
		List<Node> rList = new ArrayList<>();
		rList.add(s);
		rList.add(t);
		HelperNode root = getNodeByElements(rList);		
		
		Queue<HelperNode> queue = new LinkedList<>();
		queue.add(root);
		root.setVisited(true);
		while (!queue.isEmpty()) {
			HelperNode node = queue.remove();	
			
			List<HelperNode> unvisitedChildren = getUnvisitedChildren(node);
			for (HelperNode unvisited : unvisitedChildren) {
				String in = getInput(node, unvisited);
				unvisited.getBuilder().append(node.getBuilder().toString()).append(";").append(in);
				if (isSingleton(unvisited)) {
					return unvisited.getBuilder().toString();
				}
				
				unvisited.setVisited(true);
				queue.add(unvisited);
			}
		}

		return "-";
	}
	
	/**
	 * Sets the helper node to initial state.
	 */
	private void clearGraph() {
		for (HelperNode node : correspondingMachine.getNodes()) {
			node.setBuilder(new StringBuilder(""));
			node.setVisited(false);
		}
	}
	
	/**
	 * Given two nodes, it gives back the input label of the edge between them.
	 * @param from - starting point of the edge
	 * @param to - ending point of the edge
	 * @return - the input label of the edge 
	 */
	private String getInput(HelperNode from, HelperNode to) {
		for (HelperEdge edge : from.getEdges()) {
			if (edge.getEndNode().equals(to)) {
				return edge.getInput();
			}
		}
		return "";
	}
	
	/**
	 * Returns if the helper node contains only one item.
	 * @param node
	 * @return
	 */
	private boolean isSingleton(HelperNode node) {
		return node.getNodes().size() == 1;
	}
	
	/**
	 * Returns the unvisited children of a given node. 
	 * @param node
	 * @return
	 */
	private List<HelperNode> getUnvisitedChildren(HelperNode node) {
		List<HelperNode> unvisited = new ArrayList<>();
		for (HelperEdge edge : node.getEdges()) {
			if (!edge.getEndNode().isVisited()) {
				unvisited.add(edge.getEndNode());
			}
		}
		return unvisited;
	}

}
