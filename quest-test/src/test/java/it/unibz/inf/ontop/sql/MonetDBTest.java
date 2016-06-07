package it.unibz.inf.ontop.sql;

import it.unibz.inf.ontop.io.ModelIOManager;
import it.unibz.inf.ontop.model.OBDADataFactory;
import it.unibz.inf.ontop.model.OBDAModel;
import it.unibz.inf.ontop.model.impl.OBDADataFactoryImpl;
import it.unibz.inf.ontop.owlrefplatform.core.QuestConstants;
import it.unibz.inf.ontop.owlrefplatform.core.QuestPreferences;
import it.unibz.inf.ontop.owlrefplatform.owlapi.*;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.File;

public class MonetDBTest {

        final String owlfile = "src/test/resources/example/booktutorial.owl";
        final String obdafile = "src/test/resources/example/booktutorial.obda";

//    final String owlfile = "src/test/resources/example/exampleBooks.owl";
//    final String obdafile = "src/test/resources/example/exampleBooks.obda";

        @Test
        public void runQuery() throws Exception {
            /* 
             * Load the ontology from an external .owl file. 
            */
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File(owlfile));

            /* 
            * Load the OBDA model from an external .obda file 
            */
            OBDADataFactory fac = OBDADataFactoryImpl.getInstance();
            OBDAModel obdaModel = fac.getOBDAModel();
            ModelIOManager ioManager = new ModelIOManager(obdaModel);
            ioManager.load(obdafile);

            /* 
            * * Prepare the configuration for the Quest instance. The example below shows the setup for 
            * * "Virtual ABox" mode 
            */
            QuestPreferences preference = new QuestPreferences();
            preference.setCurrentValueOf(QuestPreferences.ABOX_MODE, QuestConstants.VIRTUAL);

            /* 
            * Create the instance of Quest OWL reasoner. 
            */
            QuestOWLFactory factory = new QuestOWLFactory();
            QuestOWLConfiguration config = QuestOWLConfiguration.builder().obdaModel(obdaModel).preferences(preference).build();
            QuestOWL reasoner = factory.createReasoner(ontology, config);

            /* 
            * Prepare the data connection for querying. 
            */
            QuestOWLConnection conn = reasoner.getConnection();
            QuestOWLStatement st = conn.createStatement();

            /* 
            * Get the book information that is stored in the database 
            */
            String sparqlQuery =
                    "PREFIX : <http://meraka/moss/exampleBooks.owl#>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "select ?x ?y where {?x rdf:type :Author. ?x :name ?y. FILTER regex(?y,\"Carr\")}";
//                    "PREFIX : <http://meraka/moss/exampleBooks.owl#>\n" +
//                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
//                    "select ?title ?author ?genre ?edition where {\n" +
//                    "?x a :Book; :title ?title; :genre ?genre; :writtenBy ?y; :hasEdition ?z.\n" +
//                            "?y a :Author; :name ?author.\n"+
//                        "?z a :Edition; :editionNumber ?edition}";
//                    "PREFIX : <http://meraka/moss/exampleBooks.owl#>\n"+
//                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
//                    "select ?x ?y ?z where {?x rdf:type :SpecialEdition. ?x :dateOfPublication ?y. ?x :editionNumber ?z}";

            try {
                long t1 = System.currentTimeMillis();
                QuestOWLResultSet rs = st.executeTuple(sparqlQuery);
                int columnSize = rs.getColumnCount();
                while (rs.nextRow()) {
                    for (int idx = 1; idx <= columnSize; idx++) {
                        OWLObject binding = rs.getOWLObject(idx);
                        System.out.print(binding.toString() + ", ");
                    }
                    System.out.print("\n");
                }
                rs.close();
                long t2 = System.currentTimeMillis();

                /* 
                * Print the query summary 
                */
                QuestOWLStatement qst = (QuestOWLStatement) st;
                String sqlQuery = qst.getUnfolding(sparqlQuery);
                System.out.println();
                System.out.println("The input SPARQL query:");
                System.out.println("=======================");
                System.out.println(sparqlQuery);
                System.out.println();
                System.out.println("The output SQL query:");
                System.out.println("=====================");
                System.out.println(sqlQuery);
                System.out.println("Query Execution Time:");
                System.out.println("=====================");
                System.out.println((t2-t1) + "ms");

            } finally {
            /* 
            * Close connection and resources 
            * */
                if (st != null && !st.isClosed()) {
                    st.close();
                }

            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
            reasoner.dispose();
        }
        }
/** 
* Main client program 
 * */
public static void main(String[] args) {
    try {
        MonetDBTest example = new MonetDBTest();
        example.runQuery();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
