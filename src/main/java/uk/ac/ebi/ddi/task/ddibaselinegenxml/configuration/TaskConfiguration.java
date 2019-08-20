package uk.ac.ebi.ddi.task.ddibaselinegenxml.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableTask
@Configuration
@EnableConfigurationProperties({DdiBaselineTaskProperties.class})
@ComponentScan({ "uk.ac.ebi.ddi.ddifileservice"})
public class TaskConfiguration {

}
