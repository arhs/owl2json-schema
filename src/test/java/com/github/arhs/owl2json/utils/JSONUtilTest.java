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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class JSONUtilTest {

    @Test
    public void test() {
        final String rawJson = "{\n" +
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
        final JsonObject jsonObject = JSONUtil.getJsonObject(rawJson);

        JsonElement el = jsonObject.get("http://www.w3.org/2002/07/owl#sameAs");
        assertTrue(el.isJsonArray());
        assertEquals(2, el.getAsJsonArray().size());
        assertEquals("http://publications.europa.eu/resource/uriserv/OJ.C_.2017.279.FULL.ENG.xhtml", el.getAsJsonArray().get(0).getAsString());
        assertEquals("http://publications.europa.eu/resource/oj/JOC_2017_279_R.ENG.xhtml", el.getAsJsonArray().get(1).getAsString());

        el = jsonObject.get("http://publications.europa.eu/ontology/cdm/cmr#creationDate");
        assertTrue(el.isJsonArray());
        assertEquals(1, el.getAsJsonArray().size());
        assertEquals("2017-08-23T06:15:11.272Z", el.getAsJsonArray().get(0).getAsString());

        el = jsonObject.get("http://publications.europa.eu/ontology/cdm#manifestation_manifests_expression");
        assertEquals("http://publications.europa.eu/resource/oj/JOC_2017_279_R.ENG", el.getAsString());

        el = jsonObject.get("http://publications.europa.eu/ontology/cdm#manifestation_type");
        assertEquals("xhtml", el.getAsString());

        el = jsonObject.get("http://publications.europa.eu/ontology/cdm#manifestation_is-signed");
        assertEquals(false, el.getAsBoolean());

        el = jsonObject.get("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        assertTrue(el.isJsonArray());
        assertEquals(1, el.getAsJsonArray().size());
        assertEquals("http://publications.europa.eu/ontology/cdm#manifestation", el.getAsJsonArray().get(0).getAsString());
    }

}