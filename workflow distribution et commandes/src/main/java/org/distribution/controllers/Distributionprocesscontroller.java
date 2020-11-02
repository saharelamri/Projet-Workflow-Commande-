package org.distribution.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.distribution.dto.CommandRequest;
import org.distribution.dto.ProcessusInstanceResponse;
import org.distribution.dto.TaskDetails;
import org.distribution.metier.DistributionprocessService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@CrossOrigin("*")
public class Distributionprocesscontroller {
	@Autowired
	DistributionprocessService procservice;
	
	protected final RepositoryService repositoryService ;
	 public Distributionprocesscontroller(RepositoryService repositoryservice) {
		    this.repositoryService = repositoryservice;
		    }

	    //********************************************************** deployment endpoints **********************************************************
	    @PostMapping("/deploy")
	    public void deployWorkflow() {
	        procservice.deployProcessDefinition();
	    }

	    //********************************************************** process endpoints **********************************************************
	    @PostMapping("/command/apply")
	    public ProcessusInstanceResponse applyHoliday(@RequestBody CommandRequest cmdrequest) {
	        return procservice.applyCommand(cmdrequest);
	    }


	    @GetMapping("/central/tasks")
	    public Collection<TaskDetails> getTasks() {
	        return procservice.getManagerTasks();
	    }


	    @PostMapping("/central/approve/tasks/{taskId}/{approved}")
	    public void approveTask(@PathVariable("taskId") String taskId,@PathVariable("approved") Boolean approved){
	        procservice.approveCommand(taskId, approved);
	    }

	    @PostMapping("/user/accept/{taskId}")
	    public void acceptHoliday(@PathVariable("taskId") String taskId){
	        procservice.acceptCommand(taskId);
	    }

	    @GetMapping("/user/tasks")
	    public Collection<TaskDetails> getUserTasks() {
	        return procservice.getUserTasks();
	    }


	    @GetMapping("/processcheck")
	    public void checkState(String processId){
	        procservice.checkProcessHistory(processId);
	    }

	    @PostMapping(value="/process")
	    public void startProcessInstance() {
	        procservice.startProcess();
	        
	    }
	    @GetMapping("/latest-definitions")
	    public List latestDefinitions() {
	        return repositoryService.createProcessDefinitionQuery()
	            .latestVersion()
	            .list()
	            .stream()
	            .map(ProcessDefinition::getKey)
	            .collect(Collectors.toList());
	    }
	   


	   
}
