package fr.filigrane.technologie.ice.reporting.surefire;

import com.google.common.io.Resources;
import fr.filigrane.technologie.ice.reporting.StepReportBuilder;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class SurefireStepReportBuilderTest {

	@Test
	public void emptyStepReport(){
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();

		assertThat(builder.generateReport()).isXmlEqualToContentOf(new File(Resources.getResource("surefire/emptyStep.xml").getPath()));
	}

	@Test
	public void sucessStepReport(){
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder
				.duration(0)
				.group("GroupName")
				.name("Name")
				.output("Message")
				.build();

		assertThat(builder.generateReport()).isXmlEqualToContentOf(new File(Resources.getResource("surefire/successStep.xml").getPath()));
	}

	@Test
	public void shortfailureStepReport(){
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder
				.duration(0)
				.group("GroupName")
				.name("Name")
				.failed()
				.build();

		assertThat(builder.generateReport())
				.isXmlEqualToContentOf(new File(Resources.getResource(
						"surefire/shortfailureStep.xml").getPath()));
	}

	@Test
	public void fullfailureStepReport(){
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder
				.duration(0)
				.group("GroupName")
				.name("Name")
				.failed("Failure message")
				.build();

		assertThat(builder.generateReport())
				.isXmlEqualToContentOf(new File(Resources.getResource(
						"surefire/fullfailureStep.xml").getPath()));
	}

	@Test
	public void shorterrorStepReport(){
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder
				.duration(0)
				.group("GroupName")
				.name("Name")
				.error()
				.build();

		assertThat(builder.generateReport())
				.isXmlEqualToContentOf(new File(Resources.getResource(
						"surefire/shorterrorStep.xml").getPath()));
	}

	@Test
	public void fullerrorStepReport(){
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder
				.duration(0)
				.group("GroupName")
				.name("Name")
				.error("Error message")
				.errorMessage("Error")
				.build();

		assertThat(builder.generateReport())
				.isXmlEqualToContentOf(new File(Resources.getResource(
						"surefire/fullerrorStep.xml").getPath()));
	}

	@Test(expected = IllegalStateException.class)
	public void output() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();
		builder.output("");
	}

	@Test(expected = IllegalStateException.class)
	public void errorMessage() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();
		builder.errorMessage("");
	}

	@Test(expected = IllegalStateException.class)
	public void status() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();
		builder.status("");
	}

	@Test
	public void status_success() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.status("PASSED");

		builder.build();
		assertThat(builder.generateReport()).isXmlEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?><testcase></testcase>");
	}

	@Test
	public void status_failed() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.status("FAILED");

		builder.build();
		assertThat(builder.generateReport()).isXmlEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?><testcase><failure></failure></testcase>");
	}

	@Test
	public void status_skipped() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.status("SKIPPED");

		builder.build();
		assertThat(builder.generateReport()).isXmlEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?><testcase><skipped></skipped></testcase>");
	}

	@Test
	public void status_error() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.status("ERROR");

		builder.build();
		assertThat(builder.generateReport()).isXmlEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?><testcase><error></error></testcase>");
	}

	@Test(expected = IllegalStateException.class)
	public void failed() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();
		builder.failed();
	}

	@Test(expected = IllegalStateException.class)
	public void failedWithMessage() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();
		builder.failed("");
	}

	@Test(expected = IllegalStateException.class)
	public void passed() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();
		builder.passed();
	}

	@Test(expected = IllegalStateException.class)
	public void skipped() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();
		builder.skipped();
	}

	@Test(expected = IllegalStateException.class)
	public void error() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();
		builder.error();
	}

	@Test(expected = IllegalStateException.class)
	public void errorWithMessage() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();
		builder.error("");
	}

	@Test(expected = IllegalStateException.class)
	public void duration() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();
		builder.duration(0);
	}

	@Test(expected = IllegalStateException.class)
	public void group() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();
		builder.group("");
	}

	@Test(expected = IllegalStateException.class)
	public void name() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();
		builder.name("");
	}

	@Test(expected = IllegalStateException.class)
	public void build() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.build();
		builder.build();
	}

	@Test(expected = IllegalStateException.class)
	public void generateReport() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		builder.generateReport();
	}

	@Test
	public void isBuild() throws Exception {
		StepReportBuilder builder = new SurefireStepReportBuilder();

		assertThat(builder.isBuild()).isFalse();

		builder.build();
		assertThat(builder.isBuild()).isTrue();
	}

}
