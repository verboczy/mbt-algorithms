package bme.mit.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

}
