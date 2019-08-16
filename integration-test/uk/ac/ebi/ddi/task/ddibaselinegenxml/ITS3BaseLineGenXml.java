package uk.ac.ebi.ddi.task.ddibaselinegenxml;


import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.ddi.ddifileservice.services.IFileSystem;
import uk.ac.ebi.ddi.task.ddibaselinegenxml.configuration.DdiBaselineTaskProperties;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DdiBaselineGenxmlApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource(properties = {
        "baselineprops.experimentFileName = /tmp/omics/ebeye_baseline_experiments_export.xml",
        "baselineprops.geneFileName = /tmp/omics/ebeye_baseline_genes_export.xml",
        "baselineprops.outputFile = data/exp/ebeye_baseline_experiments.xml",
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

    @After
    public void tearDown() throws Exception {
        fileSystem.deleteFile(ddiBaselineTaskProperties.getOutputFile());
    }

    @Test
    public void contextLoads() throws Exception {
        ddiBaselineGenxmlApplication.run();
        Assert.assertTrue(fileSystem.isFile(ddiBaselineTaskProperties.getOutputFile()));
    }
}
