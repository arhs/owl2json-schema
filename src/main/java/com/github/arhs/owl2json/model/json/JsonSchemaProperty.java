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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonSchemaProperty extends JsonSchemaEntity {

    private String description;
    @JsonProperty("default")
    private String defaultValue;
    @JsonProperty("enum")
    private List<String> enumValues;
    private Boolean readonly;
    private String format;
    private Integer minimum;
    private Integer maximum;
    private String pattern;

    public JsonSchemaProperty(final String type,
                              final String title,
                              final String description,
                              final String defaultValue,
                              final List<String> enumValues,
                              final Boolean readonly,
                              final String format,
                              final Integer minimum,
                              final Integer maximum,
                              final String pattern) {
        super(type, title);
        this.description = description;
        this.defaultValue = defaultValue;
        this.enumValues = enumValues;
        this.readonly = readonly;
        this.format = format;
        this.minimum = minimum;
        this.maximum = maximum;
        this.pattern = pattern;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(final String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<String> getEnumValues() {
        return enumValues;
    }

    public void setEnumValues(final List<String> enumValues) {
        this.enumValues = enumValues;
    }

    public Boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(final Boolean readonly) {
        this.readonly = readonly;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(final String format) {
        this.format = format;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(final Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(final Integer maximum) {
        this.maximum = maximum;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }
}
