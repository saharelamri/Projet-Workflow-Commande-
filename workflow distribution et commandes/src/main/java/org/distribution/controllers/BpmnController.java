package org.distribution.controllers;



import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;

@RestController
//@RequestMapping(path = "/api/workflow")
public class BpmnController {

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected HistoryService historyService;

    @Autowired
    protected ProcessEngineConfiguration processEngineConfiguration;

    @GetMapping(value = "/runtime/process-instances/{processInstanceId}/diagram")
    public ResponseEntity<byte[]> getProcessInstanceDiagram(@PathVariable String processInstanceId, HttpServletResponse response) {
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).list().get(0);
        HistoricProcessInstance processInstance1 = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).list().get(0);

        ProcessDefinition pde = repositoryService.getProcessDefinition(processInstance1.getProcessDefinitionId());

        List<String> listLinkIds = new ArrayList<String>();
        List<HistoricActivityInstance> historicLinkInstanceList = historyService.createHistoricActivityInstanceQuery().activityType("sequenceFlow").processInstanceId(processInstanceId).list();
        for (HistoricActivityInstance historicLinkInstance : historicLinkInstanceList){
            listLinkIds.add(historicLinkInstance.getActivityId());
        }

        Set<String> hash_Set = new HashSet<String>();
        hash_Set.add("endEvent");
        hash_Set.add("userTask");


        List<String> listActivitysIds = new ArrayList<String>();
        List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityTypes(hash_Set).orderByHistoricActivityInstanceStartTime().desc().list();
        System.out.println(historicActivityInstanceList.get(0).getActivityId());
            listActivitysIds.add(historicActivityInstanceList.get(0).getActivityId());


        if (pde != null && pde.hasGraphicalNotation()) {
            BpmnModel bpmnModel = repositoryService.getBpmnModel(pde.getId());
            ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
            InputStream resource = diagramGenerator.generateDiagram(bpmnModel, "png", listActivitysIds, listLinkIds,
                    processEngineConfiguration.getActivityFontName(), processEngineConfiguration.getLabelFontName(),
                    processEngineConfiguration.getAnnotationFontName(), processEngineConfiguration.getClassLoader(), 6.5,true);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "image/png");
            try {
                return new ResponseEntity<>(IOUtils.toByteArray(resource), responseHeaders, HttpStatus.OK);
            } catch (Exception e) {
                throw new FlowableIllegalArgumentException("Error exporting diagram", e);
            }

        } else {
            throw new FlowableIllegalArgumentException("Process instance with id '" + processInstance1.getId() + "' has no graphical notation defined.");
        }
    }





}
