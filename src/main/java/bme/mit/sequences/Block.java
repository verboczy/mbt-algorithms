package bme.mit.sequences;

import java.util.HashSet;
import java.util.Set;

import bme.mit.graph.Node;
import bme.mit.helper.Logger;

public class Block {

	private String blockName;
	private Set<Node> states;
	
	public Block() {
		this.states = new HashSet<>();
	}
	
	public Block(String name) {
		this.blockName = name;
		this.states = new HashSet<>();
	}
	
	public String getBlockName() {
		return blockName;
	}
	
	public Set<Node> getStates() {
		return states;
	}
	
	public void printMyself() {
		
				
		System.out.println("Block (" + blockName + ") contains the following states: " );
		Logger.getInstance().logMessage("[BLOCK-INFO]: block (" + blockName + ") contains the following states:");
		for (Node node : states) {
			Logger.getInstance().logMessage(node.getName());			
		}
		Logger.getInstance().logMessage("[BLOCK-INFO]: END");
	}
}
