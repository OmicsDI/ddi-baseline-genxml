package uk.ac.ebi.ddi.task.ddibaselinegenxml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.ac.ebi.ddi.ddifileservice.services.IFileSystem;
import uk.ac.ebi.ddi.expressionatlas.GenerateExpressionAtlasFile;
import uk.ac.ebi.ddi.expressionatlas.utils.FastOmicsDIReader;
import uk.ac.ebi.ddi.task.ddibaselinegenxml.configuration.DdiBaselineTaskProperties;
import uk.ac.ebi.ddi.task.ddibaselinegenxml.service.DdiBaselineGenService;
import uk.ac.ebi.ddi.xml.validator.parser.OmicsXMLFile;
import uk.ac.ebi.ddi.xml.validator.parser.model.Entry;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class DdiBaselineGenxmlApplication implements CommandLineRunner {

    public static final Logger LOGGER = LoggerFactory.getLogger(DdiBaselineGenxmlApplication.class);

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
        String tempFilePath = "tmp/tempfile.xml"; // "/mnt/home/tempexp/tempfile.xml";;
        File omicsDIFile = new File(tempFilePath);
        OmicsXMLFile experiments = new OmicsXMLFile(fileSystem.
                getFile(ddiBaselineTaskProperties.getExperimentFileName()));
        LOGGER.info("Total entries: {}", experiments.getAllEntries().size());
        List<Entry> genes = FastOmicsDIReader.getInstance().
                read(fileSystem.getFile(ddiBaselineTaskProperties.getGeneFileName()));
        LOGGER.info("Total genes: {}", genes.size());
        GenerateExpressionAtlasFile.generate(experiments, genes, omicsDIFile);
        fileSystem.copyFile(omicsDIFile, ddiBaselineTaskProperties.getOutputFile());
    }
}
