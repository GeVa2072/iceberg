package fr.filigrane.technologie.ice.reporting.surefire;

import fr.filigrane.technologie.ice.reporting.ScenarioReportBuilder;
import fr.filigrane.technologie.ice.reporting.StepReportBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SurefireScenarioReportBuilder implements ScenarioReportBuilder {
	private final ObjectFactory objectFactory;
	final Testsuite testsuite;
	private boolean build;
	private final List<StepReportBuilder> stepReportBuilders;

	public SurefireScenarioReportBuilder() {
		objectFactory = new ObjectFactory();
		testsuite = objectFactory.createTestsuite();
		build = false;
		stepReportBuilders = new ArrayList<>();
	}

	@Override
	public ScenarioReportBuilder name(final String name) {
		if(build) {
			throw new IllegalStateException("ScenarioReport already build!");
		}
		testsuite.setName(name);
		return this;
	}

	@Override
	public ScenarioReportBuilder group(final String name) {
		if(build) {
			throw new IllegalStateException("ScenarioReport already build!");
		}
		testsuite.setGroup(name);
		return this;
	}

	@Override
	public ScenarioReportBuilder duration(final long duration) {
		if(build) {
			throw new IllegalStateException("ScenarioReport already build!");
		}

		testsuite.setTests(String.valueOf((double) duration/1000));
		return this;
	}

	@Override
	public StepReportBuilder stepReportbuilder() {
		if(build) {
			throw new IllegalStateException("ScenarioReport already build!");
		}
		SurefireStepReportBuilder stepReportBuilder = new SurefireStepReportBuilder();
		stepReportBuilders.add(stepReportBuilder);
		testsuite.getTestcase().add(stepReportBuilder.testcase);
		return stepReportBuilder;
	}

	@Override
	public void build() {
		if(build) {
			throw new IllegalStateException("ScenarioReport already build!");
		}
		build = true;
		if(! stepReportBuilders.stream().allMatch(stepReportBuilder -> stepReportBuilder.isBuild())) {
			throw new IllegalStateException("ScenarioReport already build!");
		}
		testsuite.setFailures(
				String.valueOf(
						testsuite.getTestcase().stream()
								.filter(c->c.failure!=null)
								.count()));
		testsuite.setErrors(
				String.valueOf(
						testsuite.getTestcase().stream()
								.filter(c->c.error!=null)
								.count()));

		testsuite.setSkipped(
				String.valueOf(
						testsuite.getTestcase().stream()
								.filter(c->c.skipped!=null)
								.count()));

		testsuite.setTests(
				String.valueOf(
						testsuite.getTestcase().stream()
								.count()));

	}

	@Override
	public String generateReport() {
		if(!build) {
			throw new IllegalStateException("ScenarioReport already build!");
		}
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try {
			XMLStreamWriter writer = XMLOutputFactory.newFactory().createXMLStreamWriter(output);

			JAXBContext jc = JAXBContext.newInstance(Testsuite.class);
			Marshaller m = jc.createMarshaller();
			m.marshal(testsuite, writer);
			return output.toString();
		} catch (XMLStreamException | JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isBuild() {
		return build;
	}
}
