package fr.filigrane.technologie.ice.reporting;

public interface CampaignReportBuilder {


	CampaignReportBuilder name(String name);


	CampaignReportBuilder description(String description);

	ScenarioReportBuilder scenarioReportBuilder();

	void build();

	String generateReport();
}
