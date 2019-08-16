package uk.ac.ebi.ddi.task.ddibaselinegenxml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.ac.ebi.ddi.ddifileservice.services.IFileSystem;
import uk.ac.ebi.ddi.task.ddibaselinegenxml.configuration.DdiBaselineTaskProperties;
import uk.ac.ebi.ddi.task.ddibaselinegenxml.service.DdiBaselineGenService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class DdiBaselineGenxmlApplication implements CommandLineRunner {

    @Autowired
    private DdiBaselineGenService ddiBaselineGenService;

    @Autowired
    private DdiBaselineTaskProperties ddiBaselineTaskProperties;

    @Autowired
    private IFileSystem fileSystem;

    public static void main(String[] args) {
        SpringApplication.run(DdiBaselineGenxmlApplication.class, args);
    }

    public void run(String... args) throws Exception {
        Path tempFile = Paths.get("tmp");
        Files.createDirectories(tempFile);
        String tempFilePath = "tmp/tempfile.xml";
        if (ddiBaselineTaskProperties.getOutputFile().contains("/")) {
            String[] filePath = ddiBaselineTaskProperties.getOutputFile().split("/");
            tempFilePath = tempFile.toString() + "/" + filePath[filePath.length - 1];
        }
        ddiBaselineGenService.generateBaselinexml(tempFilePath,
                ddiBaselineTaskProperties.getExperimentFileName(),
                ddiBaselineTaskProperties.getGeneFileName());
        fileSystem.copyFile(new File(tempFilePath), ddiBaselineTaskProperties.getOutputFile());
    }
}
