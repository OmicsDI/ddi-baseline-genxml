package uk.ac.ebi.ddi.task.ddibaselinegenxml;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.ac.ebi.ddi.ddidomaindb.dataset.DSField;
import uk.ac.ebi.ddi.ddifileservice.services.IFileSystem;
import uk.ac.ebi.ddi.task.ddibaselinegenxml.configuration.DdiBaselineTaskProperties;
import uk.ac.ebi.ddi.task.ddibaselinegenxml.services.GeneService;
import uk.ac.ebi.ddi.xml.validator.parser.OmicsXMLFile;
import uk.ac.ebi.ddi.xml.validator.parser.marshaller.OmicsDataMarshaller;
import uk.ac.ebi.ddi.xml.validator.parser.model.Database;
import uk.ac.ebi.ddi.xml.validator.parser.model.Entry;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class DdiBaselineGenxmlApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DdiBaselineGenxmlApplication.class);

    @Autowired
    private DdiBaselineTaskProperties taskProperties;

    @Autowired
    private IFileSystem fileSystem;

    @Autowired
    private GeneService geneService;

    public static void main(String[] args) {
        SpringApplication.run(DdiBaselineGenxmlApplication.class, args);
    }

    public void run(String... args) throws Exception {
        List<String> experiments = fileSystem.listFilesFromFolder(taskProperties.getExperimentDir());
        for (String experimentFile : experiments) {
            try {
                process(experimentFile);
            } catch (Exception e) {
                LOGGER.error("Exception occurred when processing file {}", experimentFile, e);
            }
        }
    }

    private void process(String experimentFile) throws Exception {
        File omicsDIFile = File.createTempFile("omicsdi-", ".xml");
        OmicsXMLFile experiments = new OmicsXMLFile(fileSystem.getFile(experimentFile));
        OmicsDataMarshaller mm = new OmicsDataMarshaller();

        Database database = new Database();
        database.setDescription(experiments.getDescription());
        database.setName(experiments.getName());
        database.setReleaseDate(experiments.getReleaseDate());
        List<Entry> entries = experiments.getAllEntries();

        for (Entry entry : entries) {
            Set<String> geneSet = geneService.getGeneIdsFromExperiment(entry.getId());
            entry.addCrossReferenceValue(DSField.CrossRef.ENSEMBL_EXPRESSION_ATLAS.getName(), geneSet);
        }

        database.setEntryCount(entries.size());
        database.setEntries(entries);
        mm.marshall(database, new FileWriter(omicsDIFile));

        String outputFile = taskProperties.getOutputDir() + "/" + FilenameUtils.getName(experimentFile);
        fileSystem.copyFile(omicsDIFile, outputFile);
    }
}
