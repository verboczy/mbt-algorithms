package bme.mit.sequences;

import java.util.HashSet;
import java.util.Set;

import bme.mit.graph.Graph;
import bme.mit.graph.Node;

public class Partition {

	private Set<Block> partitions;
	private Set<Block> blocks;

	public Partition() {
		partitions = new HashSet<>();
		blocks = new HashSet<>();
	}

	public void currentStateUncertainty(Graph graph, String x) {
		blocks.clear();

		makePartition(graph, x);
		char[] xChars = x.toCharArray();

		for (Block block : partitions) {
			for (Node node : block.getStates()) {

				Node tempNode = node;

				for (int i = 0; i < xChars.length; i++) {
					String input = "";
					input += Character.toString(xChars[i]);

					tempNode = tempNode.getNextNode(input);
				}

				boolean blockExists = false;
				for (Block block2 : blocks) {
					if (block2.getBlockName().equals(block.getBlockName())) {
						block2.getStates().add(tempNode);
						blockExists = true;
					}
				}
				if (!blockExists) {
					Block newBlock = new Block(block.getBlockName());
					newBlock.getStates().add(tempNode);
					blocks.add(newBlock);
				}
			}
		}
	}

	public void makePartition(Graph graph, String x) {
		partitions.clear();

		for (Node node : graph.getNodes()) {
			char[] xChars = x.toCharArray();
			StringBuilder outputBuilder = new StringBuilder("");

			Node tempNode = node;

			for (int i = 0; i < xChars.length; i++) {
				String input = "";
				input += Character.toString(xChars[i]);
				String output = tempNode.getOutput(input);
				outputBuilder.append(output);
				
				tempNode = tempNode.getNextNode(input);
			}

			boolean contains = false;
			for (Block block : partitions) {
				if (block.getBlockName().equals(outputBuilder.toString())) {
					block.getStates().add(node);
					contains = true;
				}
			}
			if (!contains) {
				Block newBlock = new Block(outputBuilder.toString());
				newBlock.getStates().add(node);
				partitions.add(newBlock);
			}
		}
	}	
	

	public boolean allBlockIsSingleton() {
		for (Block block : blocks) {
			if (block.getStates().size() != 1) { // can it be 0?
				return false;
			}
		}

		return true;
	}

	public Block getNotSingletonBlock() {
		for (Block block : blocks) {
			if (block.getStates().size() != 1) {

				return block;
			}
		}

		return null;
	}

	public void printPartition() {
		for (Block block : partitions) {
			block.printMyself();
		}
	}

	public void printBlocks() {
		for (Block block : blocks) {
			block.printMyself();
		}
	}
	
}
