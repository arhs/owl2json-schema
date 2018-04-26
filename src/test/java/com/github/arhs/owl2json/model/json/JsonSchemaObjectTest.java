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

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class JsonSchemaObjectTest {
    @Test
    public void test() {
        final Map<String, JsonSchemaEntity> properties = new HashMap<>();
        final List<String> required = new ArrayList<>();
        final JsonSchemaObject object = new JsonSchemaObject("title", properties, required);

        assertEquals("title", object.getTitle());
        assertEquals(0, object.getProperties().size());
        assertEquals(0, object.getRequired().size());

        final JsonSchemaEntity entity = mock(JsonSchemaEntity.class);
        properties.put("key", entity);
        required.add("property");
        object.setProperties(properties);
        object.setRequired(required);

        assertEquals("title", object.getTitle());
        assertEquals(1, object.getProperties().size());
        assertEquals(1, object.getRequired().size());
    }
}