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

import com.github.arhs.owl2json.model.configuration.PropertyRangePreference;
import com.github.arhs.owl2json.model.configuration.PropertyRangePreferences;
import com.github.arhs.owl2json.model.json.JsonSchemaArray;
import com.github.arhs.owl2json.model.json.JsonSchemaArrayBuilder;
import com.github.arhs.owl2json.model.json.JsonSchemaEntity;
import com.github.arhs.owl2json.model.json.JsonSchemaObject;
import com.github.arhs.owl2json.model.json.JsonSchemaObjectBuilder;
import com.github.arhs.owl2json.model.json.JsonSchemaProperty;
import com.github.arhs.owl2json.model.json.JsonSchemaPropertyBuilder;
import com.github.arhs.owl2json.model.semantic.Ontology;
import com.github.arhs.owl2json.model.semantic.Property;

import java.util.List;

public class TemplateService {
    private final Ontology ontology;
    private final PropertyRangePreferences propertyRangePreferences;

    public TemplateService(final Ontology ontology, final PropertyRangePreferences propertyRangePreferences) {
        this.ontology = ontology;
        this.propertyRangePreferences = propertyRangePreferences;
    }

    public JsonSchemaObject generateTemplate(final List<String> types) {
        JsonSchemaObjectBuilder schemaBuilder = new JsonSchemaObjectBuilder().setTitle(types.toString());
        schemaBuilder.putProperty("@type", buildTypeJsonSchemaArray());

        this.generateTemplate(types, schemaBuilder);

        return schemaBuilder.createJsonSchemaObject();
    }

    private void generateTemplate(final List<String> types, JsonSchemaObjectBuilder objectBuilder) {
        final List<Property> properties = this.ontology.getPropertiesByDomainClass(types);

        for(final Property property : properties) { // properties order is implemented by the SPARQL
            JsonSchemaEntity entity;
            if(this.isArray(property)) {
                entity = this.buildJsonSchemaArray(property);
            } else {
                entity = this.buildJsonSchemaProperty(property);
                if(isRequired(property)) {
                    objectBuilder.addRequired(property.getUri());
                }
            }

            objectBuilder.putProperty(property.getUri(), entity);
        }
    }

    boolean isArray(final String property) {
        return this.ontology.getPropertyByPropertyUri(property)
                .map(this::isArray)
                .orElse(true);
    }

    boolean isArray(Property property) {
        if(property.getExactCardinality() != null && property.getExactCardinality() == 1) { // exact card has priority
            return false;
        }

        if(property.getMaximumCardinality() != null && property.getMaximumCardinality() == 1) {
            return false;
        }

        return true;
    }

    private static boolean isRequired(Property property) {
        if(property.getExactCardinality() != null && property.getExactCardinality() == 1) { // exact card has priority
            return true;
        }

        if(property.getMinimumCardinality() != null
                && property.getMinimumCardinality() == 1
                && property.getMaximumCardinality() != null
                && property.getMaximumCardinality() == 1) {
            return true;
        }

        return false;
    }

    private static JsonSchemaArray buildTypeJsonSchemaArray() {
        final JsonSchemaArrayBuilder arrayBuilder = new JsonSchemaArrayBuilder();
        arrayBuilder.setTitle("Type");
        arrayBuilder.setMinItems(1);

        final JsonSchemaPropertyBuilder propertyBuilder = new JsonSchemaPropertyBuilder().setType("string");
        propertyBuilder.setFormat("uri");
        arrayBuilder.setItems(propertyBuilder.createJsonSchemaProperty());

        return arrayBuilder.createJsonSchemaArray();
    }

    private JsonSchemaArray buildJsonSchemaArray(final Property property) {
        final JsonSchemaArrayBuilder arrayBuilder = new JsonSchemaArrayBuilder();

        if(property.getLabel() != null) {
            arrayBuilder.setTitle(property.getLabel());
        }

        final JsonSchemaPropertyBuilder propertyBuilder = new JsonSchemaPropertyBuilder().setType("string");

        if(property.getType().equalsIgnoreCase("http://www.w3.org/2002/07/owl#ObjectProperty")) {
            propertyBuilder.setFormat("uri");
        }

        this.setPropertyPreferences(propertyBuilder, property);

        arrayBuilder.setItems(propertyBuilder.createJsonSchemaProperty());

        if(property.getExactCardinality() != null) {
            arrayBuilder.setMinItems(property.getExactCardinality());
            arrayBuilder.setMaxItems(property.getExactCardinality());
        } else {
            if(property.getMinimumCardinality() != null) {
                arrayBuilder.setMinItems(property.getMinimumCardinality());
            }
            if(property.getMaximumCardinality() != null) {
                arrayBuilder.setMaxItems(property.getMaximumCardinality());
            }
        }

        return arrayBuilder.createJsonSchemaArray();
    }

    private JsonSchemaProperty buildJsonSchemaProperty(Property property) {
        final JsonSchemaPropertyBuilder builder = new JsonSchemaPropertyBuilder();
        builder.setType("string");

        if(property.getLabel() != null) {
            builder.setTitle(property.getLabel());
        }

        if(property.getType().equalsIgnoreCase("http://www.w3.org/2002/07/owl#ObjectProperty")) {
            builder.setFormat("uri");
        }

        this.setPropertyPreferences(builder, property);

        return builder.createJsonSchemaProperty();
    }

    private void setPropertyPreferences(final JsonSchemaPropertyBuilder builder, final Property property) {
        if(property.getRange() == null) {
            return;
        }

        final PropertyRangePreference propertyRangePreference = this.propertyRangePreferences.getPreferences().get(property.getRange());

        if(propertyRangePreference != null) {
            if(propertyRangePreference.getFormat() != null) {
                builder.setFormat(propertyRangePreference.getFormat());
            }

            if(propertyRangePreference.getMaximum() != null) {
                builder.setMaximum(propertyRangePreference.getMaximum());
            }

            if(propertyRangePreference.getMinimum() != null) {
                builder.setMinimum(propertyRangePreference.getMinimum());
            }

            if(propertyRangePreference.getType() != null) {
                builder.setType(propertyRangePreference.getType());
            }

            if(propertyRangePreference.getPattern() != null) {
                builder.setPattern(propertyRangePreference.getPattern());
            }
        }
    }
}
