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
package com.github.arhs.owl2json.model.configuration;

import com.github.arhs.owl2json.model.configuration.PropertyRangePreference;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PropertyRangePreferenceTest {

    @Test
    public void test() {
        final PropertyRangePreference prp = new PropertyRangePreference();

        // default values
        assertNull(prp.getFormat());
        assertNull(prp.getMaximum());
        assertNull(prp.getMinimum());
        assertNull(prp.getPattern());
        assertNull(prp.getType());

        // set values
        prp.setFormat("format");
        prp.setMaximum(Integer.MAX_VALUE);
        prp.setMinimum(Integer.MIN_VALUE);
        prp.setPattern("pattern");
        prp.setType("type");

        // check value
        assertEquals("format", prp.getFormat());
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), prp.getMaximum());
        assertEquals(Integer.valueOf(Integer.MIN_VALUE), prp.getMinimum());
        assertEquals("pattern", prp.getPattern());
        assertEquals("type", prp.getType());
    }
}