package uk.ac.ebi.ddi.task.ddibaselinegenxml.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("baseline")
public class DdiBaselineTaskProperties {

    private String experimentDir;

    private String genesDir;

    private String outputDir;

    public String getExperimentDir() {
        return experimentDir;
    }

    public void setExperimentDir(String experimentDir) {
        this.experimentDir = experimentDir;
    }

    public String getGenesDir() {
        return genesDir;
    }

    public void setGenesDir(String genesDir) {
        this.genesDir = genesDir;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }


}
