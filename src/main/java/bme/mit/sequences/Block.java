package bme.mit.sequences;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bme.mit.graph.Node;

public class Block {

	Logger log = LoggerFactory.getLogger(Block.class);
	
	private String blockName;
	private Set<Node> states;
		
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

}
