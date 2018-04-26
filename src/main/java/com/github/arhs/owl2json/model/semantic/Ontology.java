/*
 * MIT License
 *
 * Copyright (c) 2018 ARHS Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.arhs.owl2json.model.semantic;

import com.github.arhs.owl2json.utils.QueriesUtil;
import com.github.arhs.owl2json.utils.SPARQLUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Ontology {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ontology.class);
    private final OntModel ontology;

    public Ontology(final String ontologyPolicyPath,
                    final String ontologyMainPath) {
        final OntDocumentManager ontologyManager = new OntDocumentManager(ontologyPolicyPath);
        ontologyManager.clearCache();

        final OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM;
        ontModelSpec.setDocumentManager(ontologyManager);

        this.ontology = ModelFactory.createOntologyModel();
        this.ontology.read(FileManager.get().open(ontologyMainPath), "RDF/XML");

        LOGGER.info("{} triples have been loaded as ontology model", this.ontology.size());
    }

    public OntModel getModel() {
        return this.ontology;
    }

    public Optional<Property> getPropertyByPropertyUri(final String propertyUri) {
        final Query query = SPARQLUtil.buildParametrizedQuery(QueriesUtil.QUERY_BY_PROPERTY, Collections.singletonMap("prop", propertyUri));
        final ResultSet resultSet = SPARQLUtil.select(this.getModel(), query);

        if (resultSet.hasNext()) {
            final QuerySolution querySolution = resultSet.next();

            final Resource propertyTypeResource = querySolution.getResource("type");
            final String propertyType = propertyTypeResource.getURI();

            final Literal labelLiteral = querySolution.getLiteral("label");
            final String label = (labelLiteral == null ? null : labelLiteral.getString());

            final Resource domain = querySolution.getResource("domain");
            final String domainUri = (domain == null ? null : domain.getURI());

            final Resource range = querySolution.getResource("range");
            final String rangeUri = (range == null ? null : range.getURI());

            final Literal exactCardLiteral = querySolution.getLiteral("exactcard");
            final Integer exactCardinality = (exactCardLiteral == null ? null : exactCardLiteral.getInt());

            final Literal minCardLiteral = querySolution.getLiteral("mincard");
            final Integer minimumCardinality = (minCardLiteral == null ? null : minCardLiteral.getInt());

            final Literal maxCardLiteral = querySolution.getLiteral("maxcard");
            final Integer maximumCardinality = (maxCardLiteral == null ? null : maxCardLiteral.getInt());

            return Optional.of(new Property(propertyUri, propertyType, label, domainUri, rangeUri, exactCardinality, minimumCardinality, maximumCardinality));
        }

        return Optional.empty();
    }

    public List<Property> getPropertiesByDomainClass(final List<String> domainClasses) {
        final List<Property> properties = new LinkedList<>();
        final Query query = SPARQLUtil.buildParametrizedQueryWithPlaceholders(QueriesUtil.QUERY_BY_DOMAIN, Collections.singletonMap("$classes", "<" + StringUtils.join(domainClasses, "> <") + ">"));
        final ResultSet resultSet = SPARQLUtil.select(this.getModel(), query);

        while (resultSet.hasNext()) {
            final QuerySolution querySolution = resultSet.next();

            final Resource propResource = querySolution.getResource("prop");
            final String propertyUri = propResource.getURI();

            final Resource propertyTypeResource = querySolution.getResource("type");
            final String propertyType = propertyTypeResource.getURI();

            final Literal labelLiteral = querySolution.getLiteral("label");
            final String label = (labelLiteral == null ? null : labelLiteral.getString());

            final Resource domain = querySolution.getResource("domain");
            final String domainUri = (domain == null ? null : domain.getURI());

            final Resource range = querySolution.getResource("range");
            final String rangeUri = (range == null ? null : range.getURI());

            final Literal exactcardLiteral = querySolution.getLiteral("exactcard");
            final Integer exactCardinality = (exactcardLiteral == null ? null : exactcardLiteral.getInt());

            final Literal mincardLiteral = querySolution.getLiteral("mincard");
            final Integer minimumCardinality = (mincardLiteral == null ? null : mincardLiteral.getInt());

            final Literal maxcardLiteral = querySolution.getLiteral("maxcard");
            final Integer maximumCardinality = (maxcardLiteral == null ? null : maxcardLiteral.getInt());

            properties.add(new Property(propertyUri, propertyType, label, domainUri, rangeUri, exactCardinality, minimumCardinality, maximumCardinality));
        }

        return properties;
    }
}
