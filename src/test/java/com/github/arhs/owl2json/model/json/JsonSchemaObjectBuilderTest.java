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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;


public class JsonSchemaObjectBuilderTest {

    @Test
    public void test() {
        final Map<String, JsonSchemaEntity> properties = Collections.emptyMap();
        final List<String> required = Collections.emptyList();
        final JsonSchemaObjectBuilder builder = new JsonSchemaObjectBuilder();

        assertEquals(builder.setTitle("title")
                .setProperties(properties)
                .setRequired(required), builder);
        JsonSchemaObject jsonSchemaObject = builder.createJsonSchemaObject();

        assertEquals("title", jsonSchemaObject.getTitle());
        assertEquals(0, jsonSchemaObject.getProperties().size());
        assertEquals(0, jsonSchemaObject.getRequired().size());
    }

    @Test
    public void putPropertyTest() {
        final JsonSchemaObjectBuilder builder = new JsonSchemaObjectBuilder();
        final JsonSchemaEntity entity = mock(JsonSchemaEntity.class);

        assertNull(builder.createJsonSchemaObject().getProperties());

        // a new map should be created to host this key
        assertEquals(builder.putProperty("key", entity), builder);
        assertEquals(1, builder.createJsonSchemaObject().getProperties().size());

        // the map already exists, the new value should be added
        assertEquals(builder.putProperty("key2", null), builder);
        assertEquals(2, builder.createJsonSchemaObject().getProperties().size());

        // already defined key
        builder.putProperty("key", entity);
        assertEquals(2, builder.createJsonSchemaObject().getProperties().size());
        assertEquals(entity, builder.createJsonSchemaObject().getProperties().get("key"));
    }

    @Test
    public void addRequiredTest() {
        final JsonSchemaObjectBuilder builder = new JsonSchemaObjectBuilder();
        final JsonSchemaEntity entity = mock(JsonSchemaEntity.class);

        assertNull(builder.createJsonSchemaObject().getRequired());

        // a new list should be created to host this value
        assertEquals(builder.addRequired("required"), builder);
        assertEquals(1, builder.createJsonSchemaObject().getRequired().size());

        // the list already exists, the new value should be added
        assertEquals(builder.addRequired("required 2"), builder);
        assertEquals(2, builder.createJsonSchemaObject().getRequired().size());
    }
}