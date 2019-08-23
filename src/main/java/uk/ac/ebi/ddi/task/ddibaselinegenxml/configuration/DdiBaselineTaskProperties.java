package uk.ac.ebi.ddi.task.ddibaselinegenxml.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("baseline")
public class DdiBaselineTaskProperties {

    private String experimentFile;

    private String genesDir;

    private String outputFile;

    public String getExperimentFile() {
        return experimentFile;
    }

    public void setExperimentFile(String experimentFile) {
        this.experimentFile = experimentFile;
    }

    public String getGenesDir() {
        return genesDir;
    }

    public void setGenesDir(String genesDir) {
        this.genesDir = genesDir;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }


}
