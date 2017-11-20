package fr.filigrane.technologie.ice.reporting.surefire;

import com.google.common.io.Resources;
import fr.filigrane.technologie.ice.reporting.ScenarioReportBuilder;
import fr.filigrane.technologie.ice.reporting.StepReportBuilder;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class SurefireScenarioReportBuilderTest {
	@Test
	public void isBuild() throws Exception {
		ScenarioReportBuilder scenarioReportBuilder = new SurefireScenarioReportBuilder();

		assertThat(scenarioReportBuilder.isBuild()).isFalse();
		scenarioReportBuilder.build();
		assertThat(scenarioReportBuilder.isBuild()).isTrue();


	}

	@Test
	public void emptyScenarioReport(){
		ScenarioReportBuilder scenarioReportBuilder = new SurefireScenarioReportBuilder();

		scenarioReportBuilder.build();

		assertThat(scenarioReportBuilder.generateReport()).isXmlEqualToContentOf(new File(Resources.getResource("surefire/emptyScenario.xml").getPath()));
	}

	@Test
	public void sucessScenarioReport(){
		ScenarioReportBuilder scenarioReportBuilder = new SurefireScenarioReportBuilder();
		scenarioReportBuilder
				.duration(1000)
				.group("GroupName")
				.name("Name")
				.stepReportbuilder()
					.duration(0)
					.group("GroupName")
					.name("Name")
					.output("Message")
					.build();

		scenarioReportBuilder.build();
		assertThat(scenarioReportBuilder.generateReport()).isXmlEqualToContentOf(new File(Resources.getResource("surefire/successScenario.xml").getPath()));
	}

	@Test
	public void shortfailureScenarioReport(){
		ScenarioReportBuilder scenarioReportBuilder = new SurefireScenarioReportBuilder();
		scenarioReportBuilder
				.duration(1000)
				.group("GroupName")
				.name("Name")
				.stepReportbuilder()
					.duration(0)
					.group("GroupName")
					.name("Name")
					.failed()
					.build();

		scenarioReportBuilder.build();

		assertThat(scenarioReportBuilder.generateReport())
				.isXmlEqualToContentOf(new File(Resources.getResource(
						"surefire/shortfailureScenario.xml").getPath()));
	}

	@Test
	public void shorterrorStepReport(){
		ScenarioReportBuilder scenarioReportBuilder = new SurefireScenarioReportBuilder();
		scenarioReportBuilder
				.duration(1000)
				.group("GroupName")
				.name("Name")
				.stepReportbuilder()
					.duration(0)
					.group("GroupName")
					.name("Name")
					.error()
					.build();

		scenarioReportBuilder.build();
		assertThat(scenarioReportBuilder.generateReport())
				.isXmlEqualToContentOf(new File(Resources.getResource(
						"surefire/shorterrorScenario.xml").getPath()));
	}

	@Test(expected = IllegalStateException.class)
	public void scenarioBuild_with_missingStep() throws Exception {
		ScenarioReportBuilder scenarioReportBuilder = new SurefireScenarioReportBuilder();
		scenarioReportBuilder.stepReportbuilder();
		scenarioReportBuilder.build();
	}


	@Test(expected = IllegalStateException.class)
	public void name() throws Exception {
		ScenarioReportBuilder scenarioReportBuilder = new SurefireScenarioReportBuilder();
		scenarioReportBuilder.build();
		scenarioReportBuilder.name("");
	}

	@Test(expected = IllegalStateException.class)
	public void group() throws Exception {
		ScenarioReportBuilder scenarioReportBuilder = new SurefireScenarioReportBuilder();
		scenarioReportBuilder.build();
		scenarioReportBuilder.group("");
	}

	@Test(expected = IllegalStateException.class)
	public void duration() throws Exception {
		ScenarioReportBuilder scenarioReportBuilder = new SurefireScenarioReportBuilder();
		scenarioReportBuilder.build();
		scenarioReportBuilder.duration(0);
	}

	@Test(expected = IllegalStateException.class)
	public void stepReportbuilder() throws Exception {
		ScenarioReportBuilder scenarioReportBuilder = new SurefireScenarioReportBuilder();
		scenarioReportBuilder.build();
		scenarioReportBuilder.stepReportbuilder();
	}

	@Test(expected = IllegalStateException.class)
	public void build() throws Exception {
		ScenarioReportBuilder scenarioReportBuilder = new SurefireScenarioReportBuilder();
		scenarioReportBuilder.build();
		scenarioReportBuilder.build();
	}

	@Test(expected = IllegalStateException.class)
	public void generateReport() throws Exception {
		ScenarioReportBuilder scenarioReportBuilder = new SurefireScenarioReportBuilder();
		scenarioReportBuilder.generateReport();
	}

}
