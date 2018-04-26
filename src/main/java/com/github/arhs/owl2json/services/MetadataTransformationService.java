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

import com.github.arhs.owl2json.model.semantic.Ontology;
import com.github.arhs.owl2json.utils.JSONLDUtil;
import com.github.arhs.owl2json.utils.JSONUtil;
import com.github.arhs.owl2json.utils.ModelUtil;
import com.github.jsonldjava.core.JsonLdError;
import com.google.gson.JsonObject;
import org.apache.jena.datatypes.BaseDatatype;
import org.apache.jena.rdf.model.AnonId;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.RDFVisitor;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MetadataTransformationService {

    private final Ontology ontology;
    private final TemplateService templateService;

    public MetadataTransformationService(final Ontology ontology, final TemplateService templateService) {
        this.ontology = ontology;
        this.templateService = templateService;
    }


    public Model toModel(final String json, final String identifier) {
        final JsonObject jsonObject = JSONUtil.getJsonObject(json);
        jsonObject.addProperty("@id", identifier);

        try {
            return JSONLDUtil.toModel(jsonObject.toString(), ContextService.getContext(this.ontology, jsonObject.keySet()));
        } catch (IOException | JsonLdError e) {
            throw new MetadataTransformationException("JSON to Model transformation failed", e);
        }
    }

    public Map<String, Object> toFormData(final Model objectModel, String iri, final List<String> types) {
        final Map<String, Object> formData = new HashMap<>();

        objectModel.listStatements()
                .filterDrop(statement -> statement.getPredicate().equals(RDF.type)) // rdf:type is provided via @type
                .forEachRemaining(statement -> {
            final Property predicate = statement.getPredicate();
            final RDFNode object = statement.getObject();
            final Object value = object.visitWith(new RDFVisitor() {
                @Override
                public Object visitBlank(final Resource resource, final AnonId anonId) {
                    return null;
                }

                @Override
                public Object visitURI(final Resource resource, final String uri) {
                    return uri;
                }

                @Override
                public Object visitLiteral(final Literal literal) {
                    final Object value = literal.getValue();

                    if(value instanceof Boolean ||
                            value instanceof Integer ||
                            value instanceof Long ||
                            value instanceof Double ||
                            value instanceof Short ||
                            value instanceof Float ||
                            value instanceof Byte ||
                            value instanceof Character) {
                        return value;
                    }

                    if(value instanceof BaseDatatype.TypedValue) {
                        BaseDatatype.TypedValue typedValue = (BaseDatatype.TypedValue) value;
                        return typedValue.lexicalValue;
                    }

                    return value.toString();
                }
            });

            if(value != null) {
                final Object previousFormDataValue = formData.get(predicate.getURI());

                if(previousFormDataValue == null) {
                    // the property is not available in the map
                    if(this.templateService.isArray(predicate.getURI())) {
                        final List<Object> values = new LinkedList<>();
                        values.add(value);
                        formData.put(predicate.getURI(), values);
                    } else {
                        formData.put(predicate.getURI(), value);
                    }
                } else {
                    // there is a least one value in the map for this property
                    if(this.templateService.isArray(predicate.getURI())) {
                        if(!(previousFormDataValue instanceof List)) {
                            throw new MetadataTransformationException("Model to Form Data transformation failed - " + predicate.getURI() + " is expecting an array");
                        }
                        ((List)previousFormDataValue).add(value);
                    } else {
                        throw new MetadataTransformationException("Model to Form Data transformation failed - several values for a non-array entry");
                    }

                }
            }

        });

        formData.put("@type", types);
        formData.put("http://www.w3.org/2002/07/owl#sameAs", ModelUtil.resolveSameAses(objectModel, iri));

        return formData;
    }
}
