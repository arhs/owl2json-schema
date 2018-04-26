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
package com.github.arhs.owl2json.services;

import com.github.arhs.owl2json.model.configuration.PropertyRangePreferences;
import com.github.arhs.owl2json.model.json.JsonSchemaObject;
import com.github.arhs.owl2json.model.semantic.Ontology;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TemplateServiceTest {

    private static final String ONTOLOGY_PATH = "ontology/cdm.rdf";
    private static final String ONTOLOGY_POLICY_PATH = "ontology/ont-policy.rdf";
    private static Ontology ontology;
    private static PropertyRangePreferences preferences;

    @BeforeClass
    public static void setup() throws IOException {
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        ontology = new Ontology(
                classLoader.getResource(ONTOLOGY_PATH).getPath(),
                classLoader.getResource(ONTOLOGY_POLICY_PATH).getPath()
        );

        preferences = new PropertyRangePreferences();
    }

    @Test
    public void test() {
        final TemplateService templateService = new TemplateService(ontology, preferences);
        final JsonSchemaObject template = templateService.generateTemplate(Collections.singletonList("http://publications.europa.eu/ontology/cdm#work"));

        assertEquals("[http://publications.europa.eu/ontology/cdm#work]", template.getTitle());
        assertEquals("object", template.getType());

        assertNull(template.getRequired());

        assertEquals(1, template.getProperties().size());
    }
}