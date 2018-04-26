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

import java.util.List;

public class JsonSchemaPropertyBuilder {
    private String type;
    private String title;
    private String description;
    private String defaultValue;
    private List<String> enumValues;
    private Boolean readonly;
    private String format;
    private Integer minimum;
    private Integer maximum;
    private String pattern;

    public JsonSchemaPropertyBuilder setType(final String type) {
        this.type = type;
        return this;
    }

    public JsonSchemaPropertyBuilder setTitle(final String title) {
        this.title = title;
        return this;
    }

    public JsonSchemaPropertyBuilder setDescription(final String description) {
        this.description = description;
        return this;
    }

    public JsonSchemaPropertyBuilder setDefaultValue(final String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public JsonSchemaPropertyBuilder setEnumValues(final List<String> enumValues) {
        this.enumValues = enumValues;
        return this;
    }

    public JsonSchemaPropertyBuilder setReadonly(final boolean readonly) {
        this.readonly = readonly;
        return this;
    }

    public JsonSchemaPropertyBuilder setFormat(final String format) {
        this.format = format;
        return this;
    }

    public JsonSchemaPropertyBuilder setMinimum(final Integer minimum) {
        this.minimum = minimum;
        return this;
    }

    public JsonSchemaPropertyBuilder setMaximum(final Integer maximum) {
        this.maximum = maximum;
        return this;
    }

    public JsonSchemaPropertyBuilder setPattern(final String pattern) {
        this.pattern = pattern;
        return this;
    }

    public JsonSchemaProperty createJsonSchemaProperty() {
        return new JsonSchemaProperty(type, title, description, defaultValue, enumValues, readonly, format, minimum, maximum, pattern);
    }
}