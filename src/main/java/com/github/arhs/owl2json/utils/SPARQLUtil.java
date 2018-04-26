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

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.Model;

import java.util.Map;

public class SPARQLUtil {

    public static Model construct(final Model model, final String queryString) {
        final Query query = QueryFactory.create(queryString);
        return construct(model, query);
    }

    public static Model construct(final Model model, final Query query) {
        try (final QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            return qexec.execConstruct();
        }
    }

    public static ResultSet select(final Model model, final String queryString) {
        final Query query = QueryFactory.create(queryString);
        return select(model, query);
    }

    public static ResultSet select(final Model model, final Query query) {
        try (final QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            results = ResultSetFactory.copyResults(results);
            return results; // Passes the result set out of the try-resources
        }
    }

    public static boolean ask(final Model model, final Query query) {
        try (final QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            return qexec.execAsk();
        }
    }

    public static Query buildParametrizedQuery(final String queryString, final Map<String, String> uriParams) {
        final ParameterizedSparqlString queryObj = new ParameterizedSparqlString(queryString);
        uriParams.forEach(queryObj::setIri);
        return queryObj.asQuery();
    }

    public static Query buildParametrizedQueryWithPlaceholders(final String queryString, final Map<String, String> placeholderParams) {
        String query = queryString;

        for(Map.Entry<String, String> entry : placeholderParams.entrySet()) {
            query = query.replace(entry.getKey(), entry.getValue());
        }

        return QueryFactory.create(query);
    }

}
