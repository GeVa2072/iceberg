package fr.filigrane.technologie.ice.reporting.surefire;

import com.google.common.io.Resources;
import fr.filigrane.technologie.ice.reporting.CampaignReportBuilder;
import fr.filigrane.technologie.ice.reporting.ScenarioReportBuilder;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SurefireCampaignReportBuilderTest {

	@Test(expected = IllegalStateException.class)
	public void name() throws Exception {
		CampaignReportBuilder builder = new SurefireCampaignReportBuilder();

		builder.build();
		builder.name("");

	}

	@Test(expected = IllegalStateException.class)
	public void description() throws Exception {
		CampaignReportBuilder builder = new SurefireCampaignReportBuilder();

		builder.build();
		builder.description("");
	}

	@Test(expected = IllegalStateException.class)
	public void scenarioReportBuilder() throws Exception {
		CampaignReportBuilder builder = new SurefireCampaignReportBuilder();

		builder.build();
		builder.scenarioReportBuilder();
	}

	@Test(expected = IllegalStateException.class)
	public void build() throws Exception {
		CampaignReportBuilder builder = new SurefireCampaignReportBuilder();

		builder.build();
		builder.build();
	}

	@Test(expected = IllegalStateException.class)
	public void generateReport() throws Exception {
		CampaignReportBuilder builder = new SurefireCampaignReportBuilder();
		builder.generateReport();
	}

	@Test(expected = IllegalStateException.class)
	public void campaignBuild_with_missingSscenariop() throws Exception {
		CampaignReportBuilder campaignReportBuilder = new SurefireCampaignReportBuilder();
		campaignReportBuilder.scenarioReportBuilder();
		campaignReportBuilder.build();
	}

	@Test
	public void testZipWithCampaignNameFolder() throws IOException {
		CampaignReportBuilder campaignReportBuilder = new SurefireCampaignReportBuilder() ;

		ScenarioReportBuilder scenarioReportBuilder = campaignReportBuilder.scenarioReportBuilder();
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

		campaignReportBuilder.name("Campaign");
		campaignReportBuilder.description("desc");
		campaignReportBuilder.build();

		ByteArrayInputStream bytein = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(campaignReportBuilder.generateReport()));
		ZipInputStream in = new ZipInputStream(bytein);

		ZipEntry entry = in.getNextEntry();
		assertThat(entry.getName()).isEqualTo("Campaign/description.txt");

		entry = in.getNextEntry();
		assertThat(entry.getName()).isEqualTo("Campaign/Name.xml");

		StringWriter writer = new StringWriter();
		IOUtils.copy(in, writer);
		String theString = writer.toString();
		assertThat(theString).isXmlEqualToContentOf(new File(Resources.getResource("surefire/successScenario.xml").getPath()));
	}

	@Test
	public void testZipWithNoCampaignName() throws IOException {
		CampaignReportBuilder campaignReportBuilder = new SurefireCampaignReportBuilder() ;

		ScenarioReportBuilder scenarioReportBuilder = campaignReportBuilder.scenarioReportBuilder();
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

		campaignReportBuilder.build();

		ByteArrayInputStream bytein = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(campaignReportBuilder.generateReport()));
		ZipInputStream in = new ZipInputStream(bytein);

		ZipEntry entry = in.getNextEntry();
		assertThat(entry.getName()).isEqualTo("Name.xml");

		StringWriter writer = new StringWriter();
		IOUtils.copy(in, writer);
		String theString = writer.toString();
		assertThat(theString).isXmlEqualToContentOf(new File(Resources.getResource("surefire/successScenario.xml").getPath()));
	}

}
