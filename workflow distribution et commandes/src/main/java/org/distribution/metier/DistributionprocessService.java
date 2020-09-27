package org.distribution.metier;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import org.distribution.dto.CommandRequest;
import org.distribution.dto.ProcessusInstanceResponse;
import org.distribution.dto.TaskDetails;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;

import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NonNull
public class DistributionprocessService {

	
	public static final String TASK_CANDIDATE_GROUP = "Central";
    public static final String PROCESS_DEFINITION_KEY = "command_request";
    public static final String EMP_NAME = "agent";
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    ProcessEngine processEngine;
    @Autowired
    RepositoryService repositoryService;

    //********************************************************** deployment service methods **********************************************************
 

    public void deployProcessDefinition() {
		// TODO Auto-generated method stub
		 Deployment deployment =
	                repositoryService
	                        .createDeployment()
	                        .addClasspathResource("processes/Command_Request.bpmn20.xml")
	                        .deploy();
		 
		 /*repositoryService.createDeployment()
	        .addClasspathResource("Demande_Achat.bpmn20")
	        .deploy();
	        runtimeService.startProcessInstanceByKey("demo");*/

		
	}


    //********************************************************** process service methods **********************************************************
    @Transactional
    public ProcessusInstanceResponse applyCommand(CommandRequest cmdrequest) {

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("idcmd", cmdrequest.getIdCmd());
        variables.put("price", cmdrequest.getPrice());
        variables.put("description", cmdrequest.getDescription());
        variables.put("date_limite", cmdrequest.getDtl());
        variables.put("listedesproduits", cmdrequest.getProducts());
       
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);

        return new ProcessusInstanceResponse(processInstance.getId(), processInstance.isEnded());
    }

    @Transactional
    public List<TaskDetails> getManagerTasks() {
        List <Task> tasks =
                taskService.createTaskQuery().taskCandidateGroup(TASK_CANDIDATE_GROUP).list();
        List<TaskDetails> taskDetails = getTaskDetails(tasks);

        return taskDetails;
    }
  
    @Transactional
    private List<TaskDetails> getTaskDetails(List<Task> tasks) {
        List<TaskDetails> taskDetails = new ArrayList<>();
        for (Task task : tasks) {
            Map<String, Object> processVariables = taskService.getVariables(task.getId());
            taskDetails.add(new TaskDetails(task.getId(), task.getName(), processVariables));
        }
        return taskDetails;
    }

    	public TaskDetails getTaskDetailsbyId(String taskId)
    	{
    		TaskDetails taskDetails = new TaskDetails(null, null, null);
    	
    		Map<String, Object> processVariables= taskService.getVariables(taskId);
    		
    		taskDetails.setTaskData(processVariables);
    		return taskDetails;
    		
    	}

    public void approveCommand(String taskId,Boolean approved) {

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("approved", approved.booleanValue());
        taskService.complete(taskId, variables);
    }

    public void acceptCommand(String taskId) {
        taskService.complete(taskId);
        
    }


    public List <TaskDetails> getUserTasks() {

       List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned("agent").list();
        List<TaskDetails> taskDetails = getTaskDetails(tasks);

        return taskDetails;
    }

    @Transactional
    public void startProcess() {
        runtimeService.startProcessInstanceByKey("command_request");
    }
	public void checkProcessHistory(String processId) {

        HistoryService historyService = processEngine.getHistoryService();

        List<HistoricActivityInstance> activities =
                historyService
                        .createHistoricActivityInstanceQuery()
                        .processInstanceId(processId)
                        .finished()
                        .orderByHistoricActivityInstanceEndTime()
                        .asc()
                        .list();
        for (HistoricActivityInstance activity : activities) {
        	//String duration =  activity.getActivityId() + " took " + activity.getDurationInMillis() + " milliseconds" ;
            System.out.println(
                    activity.getActivityId() + " took " + activity.getDurationInMillis() + " milliseconds");
}
        System.out.println("\n \n \n \n");
	}
}


