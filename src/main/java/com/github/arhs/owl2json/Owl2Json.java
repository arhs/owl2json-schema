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
package com.github.arhs.owl2json;

import com.github.arhs.owl2json.model.configuration.PropertyRangePreferences;
import com.github.arhs.owl2json.model.json.JsonSchemaObject;
import com.github.arhs.owl2json.model.semantic.Ontology;
import com.github.arhs.owl2json.services.MetadataTransformationService;
import com.github.arhs.owl2json.services.TemplateService;
import org.apache.jena.rdf.model.Model;

import java.util.List;
import java.util.Map;

public class Owl2Json {

    private final MetadataTransformationService metadataTransformationService;
    private final TemplateService templateService;

    public Owl2Json(final String policyPath, final String ontologyPath) {
        final Ontology ontology = new Ontology(policyPath, ontologyPath);
        final PropertyRangePreferences preferences = new PropertyRangePreferences();

        this.templateService = new TemplateService(ontology, preferences);
        this.metadataTransformationService = new MetadataTransformationService(ontology, this.templateService);
    }

    public Owl2Json(final String policyPath, final String ontologyPath, final PropertyRangePreferences preferences) {
        final Ontology ontology = new Ontology(policyPath, ontologyPath);
        this.templateService = new TemplateService(ontology, preferences);
        this.metadataTransformationService = new MetadataTransformationService(ontology, this.templateService);
    }

    public Model toModel(final String json, final String identifier) {
        return this.metadataTransformationService.toModel(json, identifier);
    }

    public Map<String, Object> toFormData(final Model objectModel, final String iri, final List<String> types) {
        return this.metadataTransformationService.toFormData(objectModel, iri, types);
    }

    public JsonSchemaObject generateTemplate(final List<String> types) {
        return this.templateService.generateTemplate(types);
    }
}
