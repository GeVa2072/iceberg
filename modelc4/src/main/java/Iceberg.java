import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.ViewSet;

public class Iceberg {

	public static void main(String[] args) throws Exception {
		Workspace workspace = new Workspace("Iceberg", "This is Iceberg model.");
		Model model = workspace.getModel();

		SoftwareSystem softwareSystem = model.addSoftwareSystem("Software System", "My software system.");

		Person developer = model.addPerson("Developer User", "A developer using Iceberg.");
		Person tester = model.addPerson("Tester User", "A tester using Iceberg.");
		Person productOwner = model.addPerson("Product Owner User", "A PO using Iceberg.");

		developer.uses(softwareSystem, "Uses");
		tester.uses(softwareSystem, "Uses");
		productOwner.uses(softwareSystem, "Uses");

		ViewSet views = workspace.getViews();
		SystemContextView contextView = views.createSystemContextView(softwareSystem, "SystemContext", "An example of a System Context diagram.");
		contextView.addAllSoftwareSystems();
		contextView.addAllPeople();

		Styles styles = views.getConfiguration().getStyles();
		styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
		styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);

		StructurizrClient structurizrClient = new StructurizrClient("5be3907b-08f1-4d26-a6af-0ae4696747a4", "399532f1-569a-4e81-b9eb-38b10dd8f5c6");

		structurizrClient.putWorkspace(38337, workspace);
	}
}
