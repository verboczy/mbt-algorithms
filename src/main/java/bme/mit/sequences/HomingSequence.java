package bme.mit.sequences;

import bme.mit.graph.Edge;
import bme.mit.graph.Graph;
import bme.mit.graph.Node;

public class HomingSequence implements Sequence {

	private int boundary;

	/**
	 * Homing sequence.
	 */
	@Override
	public String getSequence(Graph graph) {

		boundary = graph.getEdges().size();
		StringBuilder x = new StringBuilder("");

		Partition currentUncertaity = new Partition();
		currentUncertaity.currentStateUncertainty(graph, x.toString());
		// while there is a block B element if sigma(x) with |B|>1
		while (!currentUncertaity.allBlockIsSingleton()) {

			// find two different states s,t element of B
			// let y be a separating sequence for s and t
			String y = separatingSequence(currentUncertaity);

			// x <- xy
			x.append(y);
			currentUncertaity.currentStateUncertainty(graph, x.toString());			
		}

		return x.toString();
	}

	private String separatingSequence(Partition part) {
		
		String y = "";
		Block notSingleton = part.getNotSingletonBlock();
		
		for (int i = 0; i < boundary; i++) {
			for (Node s : notSingleton.getStates()) {
				for (Node t : notSingleton.getStates()) {
					if (!s.equals(t)) {
						y = findSeparatingSequence(s, t, i);
						if (!"".equals(y)) {
							return y;
						}
					}
				}
			}
		}

		return y;
	}

	private String findSeparatingSequence(Node s, Node t, int bound) {
		
		StringBuilder y = new StringBuilder("");

		if (bound < 1) {
			return y.toString();
		}

		Node sTemp = s;
		Node tTemp = t;

		for (Edge sEdge : sTemp.getEdges()) {
			boolean foundSameInput = false;
			for (Edge tEdge : tTemp.getEdges()) {
				if (sEdge.getInputLabel().equals(tEdge.getInputLabel())) {
					foundSameInput = true;
					if (!sEdge.getOutputLabel().equals(tEdge.getOutputLabel())) {
						y.append(sEdge.getInputLabel());
						return y.toString();
					}
				}
			}
			if (!foundSameInput) {
				y.append(sEdge.getInputLabel());
				return y.toString();
			}
		}

		for (Edge sEdge : sTemp.getEdges()) {
			for (Edge tEdge : tTemp.getEdges()) {
				if (sEdge.getInputLabel().equals(tEdge.getInputLabel())) {
					y.append(sEdge.getInputLabel());
					String yRec = findSeparatingSequence(sEdge.getEndNode(), tEdge.getEndNode(), bound - 1);
					if (!"".equals(yRec)) {
						y.append(yRec);
						return y.toString();
					}
				}
			}
		}

		return y.toString();
	}

}
