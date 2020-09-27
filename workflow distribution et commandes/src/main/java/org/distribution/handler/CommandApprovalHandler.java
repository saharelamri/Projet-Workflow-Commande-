package org.distribution.handler;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class CommandApprovalHandler implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		// TODO Auto-generated method stub
        System.out.println("Approved, sending an email");

	}

}
