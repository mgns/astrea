package astrea.cli;

import astrea.generators.OwlGenerator;
import astrea.model.ShaclFromOwl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFLanguages;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "astrea", description = "generate SHACL shapes from ontologies",
    mixinStandardHelpOptions = true
)
public class CLI implements Callable {

  public static void main(String[] args) {
    System.exit(new CommandLine(new CLI()).execute(args));
  }

  @Override
  public Object call() throws Exception {

    String filename = "test.ttl";
    File file = new File(filename);

    ShaclFromOwl shaper = new OwlGenerator(new File("Astrea-KG.ttl"));

    Model ontology = ModelFactory.createDefaultModel();

    RDFDataMgr.read(ontology, new FileInputStream(file.getAbsolutePath()), null, RDFLanguages.nameToLang("TURTLE"));

    Model shapes = shaper.fromModel(ontology);

    OutputStream output = new FileOutputStream("shapes.ttl");

    shapes.write(output, "TURTLE");

    return null;
  }
}
