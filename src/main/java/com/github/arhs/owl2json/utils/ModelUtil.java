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
package com.github.arhs.owl2json.utils;

import com.google.common.collect.Sets;
import org.apache.jena.query.Query;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ModelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelUtil.class);

    public static void write(final Model model, final OutputStream os) throws IOException {
        if (!isBlank(model) && os != null) {
            try (final OutputStream modelOs = new ByteArrayOutputStream()) {
                model.write(modelOs);
                os.write(modelOs.toString().getBytes());
            }
        }
    }

    public static Model add(final Model... modelsToAdd) {
        final Model newModel = ModelFactory.createDefaultModel();

        for (final Model modelToAdd : modelsToAdd) {
            if (modelToAdd != null) {
                newModel.add(modelToAdd);
            }
        }

        return newModel;
    }

    public static boolean isBlank(final Model model) {
        return (model == null) || model.isEmpty();
    }

    public static void closeQuietly(final Model... models) {
        if (models == null) {
            return;
        }

        int i = 0;
        for (final Model model : models) {
            if (model == null) {
                continue;
            }

            if (model.isClosed()) {
                LOGGER.warn("Closing an already closed model.", new RuntimeException("Position " + ++i + " (base is 1)."));
                continue;
            }

            try {
                model.close();
            } catch (final Exception ignore) {
                LOGGER.warn("Closing model failed.", ignore);
            }
        }
    }

    public static void closeQuietly(final Collection<Model> models) {
        closeQuietly(models.toArray(new Model[models.size()]));
    }

    public static Set<String> resolveSameAses(final Model model, final String iri) {
        // if there are no occurrences of iri, return empty set
        String sparql = "ask where { { ?iri ?p ?o } union { ?s ?p ?iri } }";
        Query select = SPARQLUtil.buildParametrizedQuery(sparql, Collections.singletonMap("iri", iri));
        if (!SPARQLUtil.ask(model, select)) {
            return Sets.newHashSet();
        }

        // otherwise, select all sameAses plus iri itself
        final Set<String> sameAses = new HashSet<>();
        sparql = "prefix owl: <http://www.w3.org/2002/07/owl#> select ?sameAs where { ?iri (owl:sameAs|^owl:sameAs)* ?sameAs }";
        select = SPARQLUtil.buildParametrizedQuery(sparql, Collections.singletonMap("iri", iri));
        ResultSet result = SPARQLUtil.select(model, select);
        result.forEachRemaining(r -> sameAses.add(r.getResource("sameAs").getURI()));
        return sameAses;
    }

}
