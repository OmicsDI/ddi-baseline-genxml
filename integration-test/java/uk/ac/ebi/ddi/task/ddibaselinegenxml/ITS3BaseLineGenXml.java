package uk.ac.ebi.ddi.task.ddibaselinegenxml;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.ddi.ddifileservice.services.IFileSystem;
import uk.ac.ebi.ddi.task.ddibaselinegenxml.configuration.DdiBaselineTaskProperties;

import java.io.File;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DdiBaselineGenxmlApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource(properties = {
        "baseline.experimentDir=testing/expressionatlas/experiments",
        "baseline.genesDir=testing/expressionatlas/genes",
        "baseline.outputFile=testing/expressionatlas/out",
        "s3.env_auth=true",
        "s3.endpoint_url=https://s3.embassy.ebi.ac.uk",
        "s3.bucket_name=caas-omicsdi",
        "s3.region=eu-west-2"
})
public class ITS3BaseLineGenXml {

    @Autowired
    private DdiBaselineGenxmlApplication ddiBaselineGenxmlApplication;

    @Autowired
    private DdiBaselineTaskProperties ddiBaselineTaskProperties;

    @Autowired
    private IFileSystem fileSystem;

    @Before
    public void setUp() throws Exception {
        File experiment = getResource("e-enad-1.xml");
        fileSystem.copyFile(experiment, ddiBaselineTaskProperties.getExperimentDir() + "/e-enad-1.xml");

        experiment = getResource("e-mtab-2770.xml");
        fileSystem.copyFile(experiment, ddiBaselineTaskProperties.getExperimentDir() + "/e-mtab-2770.xml");

        File gene = getResource("e-enad-1.json");
        fileSystem.copyFile(gene, ddiBaselineTaskProperties.getGenesDir() + "/e-enad-1.json");

        gene = getResource("e-mtab-2770.json");
        fileSystem.copyFile(gene, ddiBaselineTaskProperties.getGenesDir() + "/e-mtab-2770.json");
    }

    private File getResource(String name) {
        return new File(getClass().getClassLoader().getResource(name).getFile());
    }

    @After
    public void tearDown() throws Exception {
        fileSystem.cleanDirectory(ddiBaselineTaskProperties.getOutputDir());
        fileSystem.cleanDirectory(ddiBaselineTaskProperties.getExperimentDir());
        fileSystem.cleanDirectory(ddiBaselineTaskProperties.getGenesDir());
    }

    @Test
    public void contextLoads() throws Exception {
        ddiBaselineGenxmlApplication.run();
        Assert.assertEquals(2, fileSystem.listFilesFromFolder(ddiBaselineTaskProperties.getOutputDir()).size());
    }
}
