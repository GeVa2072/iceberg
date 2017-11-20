package fr.filigrane.technologie.ice.reporting;

public interface ScenarioReportBuilder {

	ScenarioReportBuilder name(String name);

	ScenarioReportBuilder group(String name);

	ScenarioReportBuilder duration(long duration);

	StepReportBuilder stepReportbuilder();

	void build();

	String generateReport();

	boolean isBuild();
}
