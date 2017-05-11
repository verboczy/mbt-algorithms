package bme.mit.helper;

public class HelperEdge {

	private String input;
	private HelperNode startNode = new HelperNode();
	private HelperNode endNode = new HelperNode();
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public String getInput() {
		return input;
	}
	
	public void setStartNode(HelperNode node) {
		this.startNode = node;
	}
	
	public HelperNode getStartNode() {
		return startNode;
	}
	
	public void setEndNode(HelperNode node) {
		this.endNode = node;
	}
	
	public HelperNode getEndNode() {
		return endNode;
	}
}
