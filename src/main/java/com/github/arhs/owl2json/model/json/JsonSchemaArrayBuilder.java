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
import java.util.Map;

public class JsonSchemaArrayBuilder {
    private String title;
    private JsonSchemaProperty items;
    private List<Map<String, String>> defaultValues;
    private Integer minItems;
    private Integer maxItems;

    public JsonSchemaArrayBuilder setTitle(final String title) {
        this.title = title;
        return this;
    }

    public JsonSchemaArrayBuilder setItems(final JsonSchemaProperty items) {
        this.items = items;
        return this;
    }

    public JsonSchemaArrayBuilder setDefaultValues(final List<Map<String, String>> defaultValues) {
        this.defaultValues = defaultValues;
        return this;
    }

    public JsonSchemaArrayBuilder setMinItems(final int minItems) {
        this.minItems = minItems;
        return this;
    }

    public JsonSchemaArrayBuilder setMaxItems(final int maxItems) {
        this.maxItems = maxItems;
        return this;
    }

    public JsonSchemaArray createJsonSchemaArray() {
        return new JsonSchemaArray(title, items, defaultValues, minItems, maxItems);
    }
}