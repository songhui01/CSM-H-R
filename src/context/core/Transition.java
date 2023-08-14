package context.core;

import java.util.List;

public class Transition {
	private List<Object> inputList;
	private List<Object> outputList;
	private ContextAttributeState nextState;
	public ContextCategory co;
	
	public Transition(ContextCategory co){
		this.co=co;
	}

	public List<Object> getInputList() {
		return inputList;
	}

	public void setInputList(List<Object> inputList) {
		this.inputList = inputList;
	}

	public List<Object> getOutputList() {
		return outputList;
	}

	public void setOutputList(List<Object> outputList) {
		this.outputList = outputList;
	}

	public ContextAttributeState getNextState() {
		return nextState;
	}

	public void setNextState(ContextAttributeState nextState) {
		this.nextState = nextState;
	}

	public ContextCategory getCo() {
		return co;
	}

	public void setCo(ContextCategory co) {
		this.co = co;
	}
	
}
