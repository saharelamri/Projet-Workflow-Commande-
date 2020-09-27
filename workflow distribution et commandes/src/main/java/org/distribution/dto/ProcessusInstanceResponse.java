package org.distribution.dto;
import lombok.AllArgsConstructor;
import lombok.Value;
@Value
@AllArgsConstructor
public class ProcessusInstanceResponse {

	
	public ProcessusInstanceResponse(String processId, boolean isEnded) {
		super();
		this.processId = processId;
		this.isEnded = isEnded;
	}

	String processId;
	  boolean isEnded;
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public boolean isEnded() {
		return isEnded;
	}
	public void setEnded(boolean isEnded) {
		this.isEnded = isEnded;
	}
	  
}
