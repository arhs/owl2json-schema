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


public class QueriesUtil {
    public static final String QUERY_BY_DOMAIN =
            "            PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
            "            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "            PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "\n" +
            "            SELECT DISTINCT ?prop ?type ?label ?domain ?range ?exactcard ?mincard ?maxcard\n" +
            "            WHERE {\n" +
            "              VALUES ?class { $classes } .\n" +
            "              ?class rdfs:subClassOf* ?domain .\n" +
            "              ?prop rdfs:domain ?domain .\n" +
            "              ?prop rdf:type ?type .\n" +
            "\n" +
            "              OPTIONAL {\n" +
            "                ?prop rdfs:label ?label\n" +
            "              }\n" +
            "\n" +
            "              OPTIONAL {\n" +
            "                ?prop rdfs:range ?range\n" +
            "              }\n" +
            "\n" +
            "              OPTIONAL {\n" +
            "                ?s1 owl:onProperty ?prop ;\n" +
            "                owl:cardinality|owl:qualifiedCardinality ?exactcard .\n" +
            "              }\n" +
            "\n" +
            "              OPTIONAL {\n" +
            "                ?s2 owl:onProperty ?prop ;\n" +
            "                owl:maxCardinality|owl:maxQualifiedCardinality ?maxcard .\n" +
            "              }\n" +
            "\n" +
            "              OPTIONAL {\n" +
            "                ?s3 owl:onProperty ?prop ;\n" +
            "                owl:minCardinality|owl:minQualifiedCardinality ?mincard .\n" +
            "              }\n" +
            "\n" +
            "              FILTER(?type IN (<http://www.w3.org/2002/07/owl#ObjectProperty>, <http://www.w3.org/2002/07/owl#DatatypeProperty>))\n" +
            "            }\n" +
            "            ORDER BY DESC(?order)";

    public static final String QUERY_BY_PROPERTY = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
            "            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "            PREFIX cdm: <http://publications.europa.eu/ontology/cdm#>\n" +
            "            PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "\n" +
            "            SELECT DISTINCT ?type ?label ?domain ?range ?exactcard ?mincard ?maxcard\n" +
            "            WHERE {\n" +
            "              ?prop rdf:type ?type .\n" +
            "              OPTIONAL {\n" +
            "                ?prop rdfs:domain ?domain\n" +
            "              }\n" +
            "\n" +
            "              OPTIONAL {\n" +
            "                ?prop rdfs:label ?label\n" +
            "              }\n" +
            "\n" +
            "              OPTIONAL {\n" +
            "                ?prop rdfs:range ?range\n" +
            "              }\n" +
            "\n" +
            "              OPTIONAL {\n" +
            "                ?s1 owl:onProperty ?prop ;\n" +
            "                owl:cardinality|owl:qualifiedCardinality ?exactcard .\n" +
            "              }\n" +
            "\n" +
            "              OPTIONAL {\n" +
            "                ?s2 owl:onProperty ?prop ;\n" +
            "                owl:maxCardinality|owl:maxQualifiedCardinality ?maxcard .\n" +
            "              }\n" +
            "\n" +
            "              OPTIONAL {\n" +
            "                ?s3 owl:onProperty ?prop ;\n" +
            "                owl:minCardinality|owl:minQualifiedCardinality ?mincard .\n" +
            "              }\n" +
            "              FILTER(?type IN (<http://www.w3.org/2002/07/owl#ObjectProperty>, <http://www.w3.org/2002/07/owl#DatatypeProperty>))\n" +
            "            }";
}
