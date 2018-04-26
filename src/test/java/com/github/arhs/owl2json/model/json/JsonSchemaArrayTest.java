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

public class JsonSchemaArrayTest {

    @Test
    public void test() {
        final JsonSchemaProperty items = mock(JsonSchemaProperty.class);
        final List<Map<String, String>> defaultValues = Collections.emptyList();
        final JsonSchemaArray array = new JsonSchemaArray("title", items, defaultValues, 1, 3);

        assertEquals(array.getTitle(), "title");
        assertEquals(array.getDefaultValues().size(), 0);
        assertEquals(array.getItems(), items);
        assertEquals(array.getMinItems(), Integer.valueOf(1));
        assertEquals(array.getMaxItems(), Integer.valueOf(3));

        array.setItems(null);
        array.setDefaultValues(null);
        array.setMaxItems(30);
        array.setMinItems(10);

        assertNull(array.getDefaultValues());
        assertNull(array.getItems());
        assertEquals(array.getMinItems(), Integer.valueOf(10));
        assertEquals(array.getMaxItems(), Integer.valueOf(30));
    }

}