package it.unibz.inf.ontop.model.impl;

/*
 * #%L
 * ontop-obdalib-core
 * %%
 * Copyright (C) 2009 - 2014 Free University of Bozen-Bolzano
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import it.unibz.inf.ontop.exception.DuplicateMappingException;
import it.unibz.inf.ontop.mapping.MappingMetadata;
import it.unibz.inf.ontop.model.SQLPPMappingAxiom;
import it.unibz.inf.ontop.model.SQLPPMapping;


public class SQLPPMappingImpl implements SQLPPMapping {
	private final MappingMetadata mappingMetadata;

	private final ImmutableList<SQLPPMappingAxiom> mappings;
    private final ImmutableMap<String, SQLPPMappingAxiom> mappingIndexById;

    /**
     * Normal constructor. Used by the QuestComponentFactory.
     */
    public SQLPPMappingImpl(ImmutableList<SQLPPMappingAxiom> newMappings,
                            MappingMetadata mappingMetadata) throws DuplicateMappingException {

        checkDuplicates(newMappings);
        this.mappings = newMappings;
        this.mappingMetadata = mappingMetadata;
        this.mappingIndexById = indexMappingsById(mappings);
    }

    /**
     * No mapping should be duplicate among all the data sources.
     */
    private static void checkDuplicates(ImmutableList<SQLPPMappingAxiom> mappings)
            throws DuplicateMappingException {

        Set<SQLPPMappingAxiom> mappingSet = new HashSet<>(mappings);

        int duplicateCount = mappings.size() - mappingSet.size();

        /**
         * If there are some mappings, finds them
         */
        if (duplicateCount > 0) {
            Set<String> duplicateIds = new HashSet<>();
            int remaining = duplicateCount;
            for (SQLPPMappingAxiom mapping : mappings) {
                if (mappingSet.contains(mapping)) {
                    mappingSet.remove(mapping);
                }
                /**
                 * Duplicate
                 */
                else {
                    duplicateIds.add(mapping.getId());
                    if (--remaining == 0)
                        break;
                }
            }
            //TODO: indicate the source
            throw new DuplicateMappingException(String.format("Found %d duplicates in the following ids: %s",
                    duplicateCount, duplicateIds.toString()));
        }
    }

    private static ImmutableMap<String, SQLPPMappingAxiom> indexMappingsById(ImmutableList<SQLPPMappingAxiom> mappings)
            throws IllegalArgumentException {
        Map<String, SQLPPMappingAxiom> mappingIndexById = new HashMap<>();
        for (SQLPPMappingAxiom axiom : mappings) {
            String id = axiom.getId();
            if (mappingIndexById.containsKey(id)) {
                // Should have already been detected by checkDuplicates.
                throw new IllegalArgumentException(String.format("Not unique mapping ID found : %s", id));
            }
            mappingIndexById.put(id, axiom);
        }
        return ImmutableMap.copyOf(mappingIndexById);
    }

    @Override
    public SQLPPMapping clone() {
        try {
            return new SQLPPMappingImpl(mappings, mappingMetadata);
        } catch (DuplicateMappingException e) {
            throw new RuntimeException("Unexpected error (inconsistent cloning): " + e.getMessage());
        }
    }

    @Override
    public MappingMetadata getMetadata() {
		return mappingMetadata;
	}

    @Override
    public SQLPPMappingAxiom getPPMappingAxiom(String axiomId) {
        return mappingIndexById.get(axiomId);
    }

	@Override
	public ImmutableList<SQLPPMappingAxiom> getPPMappingAxioms() {
        return mappings;
	}
}