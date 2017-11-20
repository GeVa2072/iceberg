package fr.filigrane.technologie.ice.reporting.surefire;

import fr.filigrane.technologie.ice.reporting.StepReportBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayOutputStream;

public class SurefireStepReportBuilder implements StepReportBuilder {
	final Testsuite.Testcase testcase;
	private final ObjectFactory objectFactory;
	private boolean build;
	private int failureCount = 0;

	public SurefireStepReportBuilder() {
		objectFactory = new ObjectFactory();
		testcase = objectFactory.createTestsuiteTestcase();
		build = false;
	}

	@Override
	public StepReportBuilder output(final String message) {
		if(build) {
			throw new IllegalStateException("StepReport already build!");
		}

		testcase.setSystemOut(objectFactory.createTestsuiteTestcaseSystemOut(message));
		return this;
	}

	@Override
	public StepReportBuilder errorMessage(final String message) {
		if(build) {
			throw new IllegalStateException("StepReport already build!");
		}

		testcase.setSystemErr(objectFactory.createTestsuiteTestcaseSystemErr(message));
		return this;
	}

	@Override
	public StepReportBuilder status(final String status) {
		if(build) {
			throw new IllegalStateException("StepReport already build!");
		}

		switch (status) {
			case "PASSED":
				passed();
				break;
			case "FAILED":
				failed();
				break;
			case "ERROR":
				error();
				break;
			case "SKIPPED":
				skipped();
				break;
		}

		return this;
	}

	@Override
	public StepReportBuilder failed() {
		return failed(null);
	}

	@Override
	public StepReportBuilder failed(final String message) {
		if(build) {
			throw new IllegalStateException("StepReport already build!");
		}
		Testsuite.Testcase.Failure failure = objectFactory.createTestsuiteTestcaseFailure();
		if(message != null)
			failure.setMessage(message);
		testcase.getFailure().add(failure);
		failureCount++;
		return this;
	}



	@Override
	public StepReportBuilder passed() {
		if(build) {
			throw new IllegalStateException("StepReport already build!");
		}

		return this;
	}

	@Override
	public StepReportBuilder skipped() {
		if(build) {
			throw new IllegalStateException("StepReport already build!");
		}
		Testsuite.Testcase.Skipped skipped = new Testsuite.Testcase.Skipped();
		testcase.setSkipped(objectFactory.createTestsuiteTestcaseSkipped(skipped));
		return this;
	}

	@Override
	public StepReportBuilder error() {
		return error(null);
	}

	@Override
	public StepReportBuilder error(String message) {
		if(build) {
			throw new IllegalStateException("StepReport already build!");
		}
		Testsuite.Testcase.Error error = new Testsuite.Testcase.Error();
		if(message != null)
			error.setMessage(message);
		testcase.setError(objectFactory.createTestsuiteTestcaseError(error));
		return this;
	}

	@Override
	public StepReportBuilder duration(long duration) {
		if(build) {
			throw new IllegalStateException("StepReport already build!");
		}
		testcase.setTime(String.valueOf((double)duration/1000));
		return this;
	}

	@Override
	public StepReportBuilder group(String group) {
		if(build) {
			throw new IllegalStateException("StepReport already build!");
		}
		testcase.setGroup(group);
		return this;
	}

	@Override
	public StepReportBuilder name(String name) {
		if(build) {
			throw new IllegalStateException("StepReport already build!");
		}
		testcase.setName(name);
		return this;
	}

	@Override
	public void build() {
		if(build) {
			throw new IllegalStateException("StepReport already build!");
		}
		build = true;
	}

	@Override
	public String generateReport() {
		if(!build) {
			throw new IllegalStateException("StepReport already build!");
		}
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try {
			XMLStreamWriter writer = XMLOutputFactory.newFactory().createXMLStreamWriter(output);

			JAXBContext jc = JAXBContext.newInstance(Testsuite.Testcase.class);
			Marshaller m = jc.createMarshaller();
			JAXBElement<Testsuite.Testcase> element = new JAXBElement<Testsuite.Testcase>(new QName("testcase"), Testsuite.Testcase.class, testcase);
			m.marshal(element, writer);
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
