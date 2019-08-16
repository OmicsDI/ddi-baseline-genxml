package uk.ac.ebi.ddi.task.ddibaselinegenxml.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("baselineprops")
public class DdiBaselineTaskProperties {

    private String experimentFileName;

    private String geneFileName;

    private String outputFile;

    public String getExperimentFileName() {
        return experimentFileName;
    }

    public void setExperimentFileName(String experimentFileName) {
        this.experimentFileName = experimentFileName;
    }

    public String getGeneFileName() {
        return geneFileName;
    }

    public void setGeneFileName(String geneFileName) {
        this.geneFileName = geneFileName;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }


}
