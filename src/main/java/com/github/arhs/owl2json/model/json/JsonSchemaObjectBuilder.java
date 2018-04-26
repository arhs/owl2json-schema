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
package com.github.arhs.owl2json.model.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JsonSchemaObjectBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonSchemaObjectBuilder.class);
    private String title;
    private Map<String, JsonSchemaEntity> properties;
    private List<String> required;

    public JsonSchemaObjectBuilder setTitle(final String title) {
        this.title = title;
        return this;
    }

    public JsonSchemaObjectBuilder setProperties(final Map<String, JsonSchemaEntity> properties) {
        this.properties = properties;
        return this;
    }

    public JsonSchemaObjectBuilder setRequired(final List<String> required) {
        this.required = required;
        return this;
    }

    public JsonSchemaObjectBuilder putProperty(final String key, final JsonSchemaEntity value) {
        if(this.properties == null) {
            this.properties = new LinkedHashMap<>(); // Insertion order is important for display ordering
        }

        if(this.properties.containsKey(key)) {
            LOGGER.warn("There is already a property definition for {} in the template", key);
        }

        this.properties.put(key, value);

        return this;
    }

    public JsonSchemaObjectBuilder addRequired(final String value) {
        if(this.required == null) {
            this.required = new LinkedList<>();
        }

        this.required.add(value);

        return this;
    }

    public JsonSchemaObject createJsonSchemaObject() {
        return new JsonSchemaObject(title, properties, required);
    }
}