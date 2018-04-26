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

import org.apache.jena.query.Query;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

public class SPARQLUtilTest {

    private String selectQueryString;
    private String constructQueryString;
    private Map<String, String> params;
    private Model model;

    @Before
    public void setUp() throws Exception {
        selectQueryString = "SELECT * WHERE { ?s ?p ?o }";
        params = Collections.singletonMap("?s", "<http://publications.europa.eu/test/resource>");

        constructQueryString = "CONSTRUCT { ?s <http://publications.europa.eu/test/property2> ?o } WHERE { ?s <http://publications.europa.eu/test/property1> ?o }";

        model = ModelFactory.createDefaultModel();
        model.add(model.createResource("http://publications.europa.eu/test/resource"), model.createProperty("http://publications.europa.eu/test/property1"), "Value");
    }

    @Test
    public void construct() throws Exception {
        final Model output = SPARQLUtil.construct(this.model, this.constructQueryString);

        Assert.assertEquals(1, output.size());

        final StmtIterator iterator = output.listStatements();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("http://publications.europa.eu/test/property2", iterator.next().getPredicate().getURI());
    }

    @Test
    public void select() throws Exception {
        final ResultSet resultSet = SPARQLUtil.select(this.model, this.selectQueryString);

        Assert.assertTrue(resultSet.hasNext());
        Assert.assertEquals("http://publications.europa.eu/test/resource", resultSet.next().getResource("s").getURI());
    }

    @Test
    public void buildParametrizedQuery() throws Exception {
        final Map iriParams = Collections.singletonMap("?s", "http://publications.europa.eu/test/resource");
        final Query query = SPARQLUtil.buildParametrizedQuery(this.selectQueryString, iriParams);
        Assert.assertTrue(query.toString().contains("<http://publications.europa.eu/test/resource>"));
    }


    @Test
    public void buildParametrizedQueryWithPlaceholders() throws Exception {
        final Query query = SPARQLUtil.buildParametrizedQueryWithPlaceholders(this.selectQueryString, params);
        Assert.assertTrue(query.toString().contains("<http://publications.europa.eu/test/resource>"));
        Assert.assertFalse(query.toString().contains("?s"));
    }

}