package fr.filigrane.technologie.ice.reporting.cucumber;

import fr.filigrane.technologie.ice.reporting.StepReportBuilder;

public class CucumberStepReportBuilder implements StepReportBuilder {
	@Override
	public StepReportBuilder output(final String message) {
		return null;
	}

	@Override
	public StepReportBuilder errorMessage(final String message) {
		return null;
	}

	@Override
	public StepReportBuilder error(final String message) {
		return null;
	}

	@Override
	public StepReportBuilder status(final String status) {
		return null;
	}

	@Override
	public StepReportBuilder failed() {
		return null;
	}

	@Override
	public StepReportBuilder failed(final String message) {
		return null;
	}

	@Override
	public StepReportBuilder passed() {
		return null;
	}

	@Override
	public StepReportBuilder skipped() {
		return null;
	}

	@Override
	public StepReportBuilder error() {
		return null;
	}

	@Override
	public StepReportBuilder duration(final long duration) {
		return null;
	}

	@Override
	public StepReportBuilder group(final String group) {
		return null;
	}

	@Override
	public StepReportBuilder name(final String name) {
		return null;
	}

	@Override
	public void build() {

	}

	@Override
	public String generateReport() {
		return null;
	}

	@Override
	public boolean isBuild() {
		return false;
	}
}
