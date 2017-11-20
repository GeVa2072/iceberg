package fr.filigrane.technologie.ice.reporting.surefire;

import fr.filigrane.technologie.ice.reporting.CampaignReportBuilder;
import fr.filigrane.technologie.ice.reporting.ScenarioReportBuilder;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SurefireCampaignReportBuilder implements CampaignReportBuilder {
	private boolean build;
	private final List<SurefireScenarioReportBuilder> scenarioReportBuilders;
	private final ByteArrayOutputStream baos ;
	private final ZipOutputStream zos;
	private String name;
	private String description;


	public SurefireCampaignReportBuilder() {
		scenarioReportBuilders = new ArrayList<>();
		build = false;
		baos = new ByteArrayOutputStream();
		zos = new ZipOutputStream(baos);
	}


	@Override
	public CampaignReportBuilder name(final String name) {
		if(build) {
			throw new IllegalStateException("CampaignReport already build!");
		}
		this.name = name;
		return this;
	}

	@Override
	public CampaignReportBuilder description(final String description) {
		if(build) {
			throw new IllegalStateException("CampaignReport already build!");
		}
		this.description = description;
		return this;
	}

	@Override
	public ScenarioReportBuilder scenarioReportBuilder() {
		if(build) {
			throw new IllegalStateException("CampaignReport already build!");
		}
		SurefireScenarioReportBuilder scenarioReportBuilder = new SurefireScenarioReportBuilder();
		scenarioReportBuilders.add(scenarioReportBuilder);

		return scenarioReportBuilder;
	}

	@Override
	public void build() {
		if(build) {
			throw new IllegalStateException("CampaignReport already build!");
		}
		build = true;
		if(! scenarioReportBuilders.stream().allMatch(scenarioReportBuilder -> scenarioReportBuilder.isBuild())) {
			throw new IllegalStateException("CampaignReport contains non build scenarioReport !");
		}
		try {
			if (StringUtils.isNotBlank(name)) {
				//zos.putNextEntry(new ZipEntry(name));
				name+="/";
			} else {
				name = "";
			}
			if (StringUtils.isNotBlank(description)) {
				zos.putNextEntry(new ZipEntry(name+"description.txt"));
				zos.write(description.getBytes());
				zos.closeEntry();
			}
			for (SurefireScenarioReportBuilder scenarioReportBuilder : scenarioReportBuilders) {
				zos.putNextEntry(new ZipEntry( name+scenarioReportBuilder.testsuite.name+".xml"));
				zos.write(scenarioReportBuilder.generateReport().getBytes());
				zos.closeEntry();
			}
			zos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String generateReport() {
		if(!build) {
			throw new IllegalStateException("CampaignReport already build!");
		}

		BASE64Encoder enc = new BASE64Encoder();
		return enc.encode(baos.toByteArray());
	}
}
