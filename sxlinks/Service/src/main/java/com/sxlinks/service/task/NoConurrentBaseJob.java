package com.sxlinks.service.task;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class NoConurrentBaseJob extends BaseJob {
}
