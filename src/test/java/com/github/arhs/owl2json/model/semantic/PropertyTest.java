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
package com.github.arhs.owl2json.model.semantic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class PropertyTest {

    @Test
    public void testDefaultConstructor() {
        final Property property = new Property();

        assertNull(property.getUri());
        assertNull(property.getExactCardinality());
        assertNull(property.getMaximumCardinality());
        assertNull(property.getMinimumCardinality());
        assertNull(property.getLabel());
        assertNull(property.getRange());
        assertNull(property.getType());
        assertNull(property.getDomain());
    }

    @Test
    public void testConstructor() {
        final Property property = new Property(
                "uri",
                "type",
                "label",
                "domain",
                "range",
                1,
                0,
                3);

        assertEquals("uri", property.getUri());
        assertEquals(Integer.valueOf(1), property.getExactCardinality());
        assertEquals(Integer.valueOf(3), property.getMaximumCardinality());
        assertEquals(Integer.valueOf(0), property.getMinimumCardinality());
        assertEquals("label", property.getLabel());
        assertEquals("range", property.getRange());
        assertEquals("type", property.getType());
        assertEquals("domain", property.getDomain());

        // Update
        property.setUri("newUri");
        property.setExactCardinality(10);
        property.setMaximumCardinality(30);
        property.setMinimumCardinality(-1);
        property.setLabel("newLabel");
        property.setRange("newRange");
        property.setType("newType");
        property.setDomain("newDomain");

        assertEquals("newUri", property.getUri());
        assertEquals(Integer.valueOf(10), property.getExactCardinality());
        assertEquals(Integer.valueOf(30), property.getMaximumCardinality());
        assertEquals(Integer.valueOf(-1), property.getMinimumCardinality());
        assertEquals("newLabel", property.getLabel());
        assertEquals("newRange", property.getRange());
        assertEquals("newType", property.getType());
        assertEquals("newDomain", property.getDomain());

        // toString
        assertEquals(property.toString(),
                "Property{" +
                        "uri='newUri', " +
                        "type='newType', " +
                        "label='newLabel', " +
                        "domain='newDomain', " +
                        "range='newRange', " +
                        "exactCardinality=10, " +
                        "minimumCardinality=-1, " +
                        "maximumCardinality=30" +
                        "}");
    }

}