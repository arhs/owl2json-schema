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
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonSchemaArray extends JsonSchemaEntity {
    private JsonSchemaProperty items;
    @JsonProperty("default")
    private List<Map<String, String>> defaultValues;
    private Integer minItems;
    private Integer maxItems;

    public JsonSchemaArray(final String title,
                           final JsonSchemaProperty items,
                           final List<Map<String, String>> defaultValues,
                           final Integer minItems,
                           final Integer maxItems) {
        super("array", title);
        this.items = items;
        this.defaultValues = defaultValues;
        this.minItems = minItems;
        this.maxItems = maxItems;
    }

    public JsonSchemaProperty getItems() {
        return items;
    }

    public void setItems(final JsonSchemaProperty items) {
        this.items = items;
    }

    public List<Map<String, String>> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(final List<Map<String, String>> defaultValues) {
        this.defaultValues = defaultValues;
    }

    public Integer getMinItems() {
        return minItems;
    }

    public void setMinItems(final Integer minItems) {
        this.minItems = minItems;
    }

    public Integer getMaxItems() {
        return maxItems;
    }

    public void setMaxItems(final Integer maxItems) {
        this.maxItems = maxItems;
    }
}
