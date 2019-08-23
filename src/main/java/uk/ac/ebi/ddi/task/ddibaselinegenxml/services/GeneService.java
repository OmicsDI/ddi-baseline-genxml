package uk.ac.ebi.ddi.task.ddibaselinegenxml.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.ddi.ddifileservice.services.IFileSystem;
import uk.ac.ebi.ddi.task.ddibaselinegenxml.configuration.DdiBaselineTaskProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@Service
public class GeneService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneService.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private IFileSystem fileSystem;

    @Autowired
    private DdiBaselineTaskProperties taskProperties;

    public Set<String> getGeneIdsFromExperiment(String experimentId) {
        String genesFile = taskProperties.getGenesDir() + "/" + experimentId.toLowerCase() + ".json";
        Set<String> result = new HashSet<>();
        try (InputStream inputStream = fileSystem.getInputStream(genesFile)) {
            JsonNode genes = mapper.readTree(inputStream);
            for (JsonNode gene : genes) {
                result.add(gene.get("id").textValue());
            }
        } catch (IOException e) {
            LOGGER.error("Can't read genes file {} for experiment {}", genesFile, experimentId, e);
        }
        return result;
    }
}
