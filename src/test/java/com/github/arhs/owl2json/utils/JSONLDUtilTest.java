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


import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.StmtIterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JSONLDUtilTest {

    private String rawJson;
    private Map<String, Object> context;

    @Before
    public void setUp() {
        rawJson = "{\n" +
                "        \"http://www.w3.org/2002/07/owl#sameAs\": [\n" +
                "            \"http://publications.europa.eu/resource/uriserv/OJ.C_.2017.279.FULL.ENG.xhtml\",\n" +
                "            \"http://publications.europa.eu/resource/oj/JOC_2017_279_R.ENG.xhtml\"\n" +
                "        ],\n" +
                "        \"http://publications.europa.eu/ontology/cdm/cmr#creationDate\": [\n" +
                "            \"2017-08-23T06:15:11.272Z\"\n" +
                "        ],\n" +
                "        \"http://publications.europa.eu/ontology/cdm#manifestation_manifests_expression\": \"http://publications.europa.eu/resource/oj/JOC_2017_279_R.ENG\",\n" +
                "        \"http://publications.europa.eu/ontology/cdm/cmr#lastModificationDate\": \"2017-08-23T06:06:25.574Z\",\n" +
                "        \"http://publications.europa.eu/ontology/cdm#manifestation_type\": \"xhtml\",\n" +
                "        \"http://publications.europa.eu/ontology/cdm#manifestation_is-signed\": false,\n" +
                "        \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\": [\n" +
                "            \"http://publications.europa.eu/ontology/cdm#manifestation\"\n" +
                "        ]\n" +
                "    }";

        context = new HashMap<>();
        context.put("http://publications.europa.eu/ontology/cdm#manifestation_is-signed",
                Collections.singletonMap("@type", "http://www.w3.org/2001/XMLSchema#boolean"));
        context.put("http://publications.europa.eu/ontology/cdm#manifestation_manifests_expression",
                Collections.singletonMap("@type", "@id"));
    }

    @Test
    public void toModel() throws Exception {

        final Model model = JSONLDUtil.toModel(this.rawJson, context);

        StmtIterator iterator = model.listStatements(
                (Resource)null,
                (Property)model.createProperty("http://publications.europa.eu/ontology/cdm#manifestation_manifests_expression"),
                (RDFNode) null);

        Assert.assertTrue(iterator.hasNext());
        Assert.assertTrue(iterator.next().getObject().isURIResource());

        iterator = model.listStatements(
                (Resource)null,
                (Property)model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
                (RDFNode) null);

        Assert.assertTrue(iterator.hasNext());
        Assert.assertFalse(iterator.next().getObject().isURIResource());

        iterator = model.listStatements(
                (Resource)null,
                (Property)model.createProperty("http://publications.europa.eu/ontology/cdm#manifestation_is-signed"),
                (RDFNode) null);

        Assert.assertTrue(iterator.hasNext());
        RDFNode obj = iterator.next().getObject();
        Assert.assertTrue(obj.isLiteral());
        Assert.assertEquals("http://www.w3.org/2001/XMLSchema#boolean", obj.asLiteral().getDatatypeURI());
    }

}