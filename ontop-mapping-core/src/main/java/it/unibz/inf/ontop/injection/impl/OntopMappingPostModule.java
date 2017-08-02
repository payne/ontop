package it.unibz.inf.ontop.injection.impl;


import it.unibz.inf.ontop.injection.OntopMappingSettings;
import it.unibz.inf.ontop.spec.MappingExtractor;
import it.unibz.inf.ontop.spec.MappingTransformer;
import it.unibz.inf.ontop.spec.OBDASpecificationExtractor;

/**
 * POST-module: to be loaded after that all the dependencies of concrete implementations have been defined
 */
public class OntopMappingPostModule extends OntopAbstractModule {

    protected OntopMappingPostModule(OntopMappingSettings settings) {
        super(settings);
    }

    @Override
    public void configure() {
        bindFromPreferences(MappingExtractor.class);
        bindFromPreferences(MappingTransformer.class);
        bindFromPreferences(OBDASpecificationExtractor.class);
    }
}