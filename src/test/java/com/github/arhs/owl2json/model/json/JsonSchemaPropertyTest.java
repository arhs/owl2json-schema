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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class JsonSchemaPropertyTest {

    @Test
    public void test() {
        final JsonSchemaProperty property = new JsonSchemaProperty(
                "type",
                "title",
                "description",
                "default",
                null,
                false,
                "format",
                1,
                3,
                "pattern");

        assertEquals("type", property.getType());
        assertEquals("title", property.getTitle());
        assertEquals("description", property.getDescription());
        assertEquals("default", property.getDefaultValue());
        assertNull(property.getEnumValues());
        assertFalse(property.isReadonly());
        assertEquals("format", property.getFormat());
        assertEquals(Integer.valueOf(1), property.getMinimum());
        assertEquals(Integer.valueOf(3), property.getMaximum());
        assertEquals("pattern", property.getPattern());

        // update
        property.setDescription("newDescription");
        property.setDefaultValue("newDefaultValue");
        property.setEnumValues(Collections.emptyList());
        property.setReadonly(true);
        property.setFormat("newFormat");
        property.setMinimum(10);
        property.setMaximum(30);
        property.setPattern("newPattern");

        assertEquals("type", property.getType());
        assertEquals("title", property.getTitle());
        assertEquals("newDescription", property.getDescription());
        assertEquals("newDefaultValue", property.getDefaultValue());
        assertEquals(0, property.getEnumValues().size());
        assertTrue(property.isReadonly());
        assertEquals("newFormat", property.getFormat());
        assertEquals(Integer.valueOf(10), property.getMinimum());
        assertEquals(Integer.valueOf(30), property.getMaximum());
        assertEquals("newPattern", property.getPattern());
    }
}