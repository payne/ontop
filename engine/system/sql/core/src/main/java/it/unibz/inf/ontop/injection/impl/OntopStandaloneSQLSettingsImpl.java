package it.unibz.inf.ontop.injection.impl;

import com.google.common.collect.ImmutableSet;
import it.unibz.inf.ontop.injection.OntopStandaloneSQLSettings;
import it.unibz.inf.ontop.injection.OntopSystemSQLSettings;

import java.util.Optional;
import java.util.Properties;


public class OntopStandaloneSQLSettingsImpl extends OntopMappingSQLAllSettingsImpl implements OntopStandaloneSQLSettings {

    private final OntopSystemSQLSettings systemSettings;

    OntopStandaloneSQLSettingsImpl(Properties userProperties, boolean isR2rml) {
        super(loadProperties(userProperties), isR2rml);
        systemSettings = new OntopSystemSQLSettingsImpl(copyProperties());
    }

    private static Properties loadProperties(Properties userProperties) {
        Properties properties = new OntopSystemSQLSettingsImpl(userProperties).copyProperties();
        properties.putAll(userProperties);
        return properties;
    }

    @Override
    public boolean isExistentialReasoningEnabled() {
        return systemSettings.isExistentialReasoningEnabled();
    }

    @Override
    public boolean isDistinctPostProcessingEnabled() {
        return getRequiredBoolean(DISTINCT_RESULTSET);
    }

    @Override
    public boolean isQueryLoggingEnabled() {
        return getRequiredBoolean(QUERY_LOGGING);
    }

    @Override
    public boolean isQueryTemplateExtractionEnabled() {
        return systemSettings.isQueryTemplateExtractionEnabled();
    }

    @Override
    public boolean isSparqlQueryIncludedIntoQueryLog() {
        return getRequiredBoolean(SPARQL_INCLUDED_QUERY_LOGGING);
    }

    @Override
    public boolean isReformulatedQueryIncludedIntoQueryLog() {
        return getRequiredBoolean(REFORMULATED_INCLUDED_QUERY_LOGGING);
    }

    @Override
    public boolean areClassesAndPropertiesIncludedIntoQueryLog() {
        return getRequiredBoolean(CLASSES_INCLUDED_QUERY_LOGGING);
    }

    @Override
    public boolean areTablesIncludedIntoQueryLog() {
        return getRequiredBoolean(TABLES_INCLUDED_QUERY_LOGGING);
    }

    @Override
    public boolean isQueryLoggingDecompositionEnabled() {
        return getRequiredBoolean(QUERY_LOGGING_DECOMPOSITION);
    }

    @Override
    public boolean areQueryLoggingDecompositionAndMergingMutuallyExclusive() {
        return getRequiredBoolean(QUERY_LOGGING_DECOMPOSITION_AND_MERGING_EXCLUSIVE);
    }

    @Override
    public long getQueryCacheMaxSize() {
        return getRequiredLong(QUERY_CACHE_MAX_SIZE);
    }

    @Override
    public String getApplicationName() {
        return getRequiredProperty(APPLICATION_NAME);
    }

    @Override
    public ImmutableSet<String> getHttpHeaderNamesToLog() {
        return systemSettings.getHttpHeaderNamesToLog();
    }

    @Override
    public boolean isKeepAliveEnabled() {
        return systemSettings.isKeepAliveEnabled();
    }

    @Override
    public boolean isRemoveAbandonedEnabled() {
        return systemSettings.isRemoveAbandonedEnabled();
    }

    @Override
    public int getConnectionTimeout() {
        return systemSettings.getConnectionTimeout();
    }

    @Override
    public int getConnectionPoolInitialSize() {
        return systemSettings.getConnectionPoolInitialSize();
    }

    @Override
    public int getConnectionPoolMaxSize() {
        return systemSettings.getConnectionPoolMaxSize();
    }

    @Override
    public int getFetchSize() {
        return systemSettings.getFetchSize();
    }

    @Override
    public Optional<Integer> getDefaultQueryTimeout() {
        return getInteger(DEFAULT_QUERY_TIMEOUT);
    }

    @Override
    public boolean isPermanentDBConnectionEnabled() {
        return systemSettings.isPermanentDBConnectionEnabled();
    }

    @Override
    public Optional<Integer> getHttpMaxAge() {
        return systemSettings.getHttpMaxAge();
    }

    @Override
    public Optional<Integer> getHttpStaleWhileRevalidate() {
        return systemSettings.getHttpStaleWhileRevalidate();
    }

    @Override
    public Optional<Integer> getHttpStaleIfError() {
        return systemSettings.getHttpStaleIfError();
    }
}
