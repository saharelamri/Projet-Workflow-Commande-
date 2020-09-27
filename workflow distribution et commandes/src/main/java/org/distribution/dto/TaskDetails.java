package org.distribution.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;
@Data
@AllArgsConstructor
public class TaskDetails {
	 String taskId;
	  String taskName;
	  Map<String, Object> taskData;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Map<String, Object> getTaskData() {
		return taskData;
	}
	public void setTaskData(Map<String, Object> taskData) {
		this.taskData = taskData;
	}
	public TaskDetails(String taskId, String taskName, Map<String, Object> taskData) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskData = taskData;
	}

}
