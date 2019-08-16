package uk.ac.ebi.ddi.task.ddibaselinegenxml.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.ac.ebi.ddi.expressionatlas.GenerateExpressionAtlasFile;
import uk.ac.ebi.ddi.expressionatlas.utils.FastOmicsDIReader;
import uk.ac.ebi.ddi.xml.validator.parser.OmicsXMLFile;
import uk.ac.ebi.ddi.xml.validator.parser.model.Entry;

import java.io.File;
import java.util.List;

@Service
public class DdiBaselineGenService {

    public static final Logger LOGGER = LoggerFactory.getLogger(DdiBaselineGenService.class);

    public void generateBaselinexml(String outputFile, String experimentFileName,
                                    String geneFileName) throws Exception {
        File omicsDIFile = new File(outputFile);
        OmicsXMLFile experiments = new OmicsXMLFile(new File(experimentFileName));
        LOGGER.info("Total entries: {}", experiments.getAllEntries().size());
        List<Entry> genes = FastOmicsDIReader.getInstance().read(new File(geneFileName));
        LOGGER.info("Total genes: {}", genes.size());
        GenerateExpressionAtlasFile.generate(experiments, genes, omicsDIFile);
    }
}
