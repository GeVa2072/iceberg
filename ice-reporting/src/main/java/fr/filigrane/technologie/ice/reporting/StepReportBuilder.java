package fr.filigrane.technologie.ice.reporting;

public interface StepReportBuilder {

	StepReportBuilder output(String message);

	StepReportBuilder errorMessage(String message);

	StepReportBuilder error(String message);

	StepReportBuilder status(String status);

	StepReportBuilder failed();

	StepReportBuilder failed(String message);

	StepReportBuilder passed();

	StepReportBuilder skipped();

	StepReportBuilder error();

	StepReportBuilder duration(long duration);

	StepReportBuilder group(String group);

	StepReportBuilder name(String name);

	void build();

	String generateReport();

	boolean isBuild();
}
