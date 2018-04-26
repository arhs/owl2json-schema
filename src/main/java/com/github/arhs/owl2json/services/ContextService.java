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
package com.github.arhs.owl2json.services;

import com.github.arhs.owl2json.model.semantic.Ontology;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ContextService {

    private static final Map<String, String> DEFAULT_TYPE_ID_CONTEXT_ENTRY = Collections.singletonMap("@type", "@id");

    public static Map<String, Object> getContext(final Ontology ontology, final Set<String> properties) {
        final Map<String, Object> context = new HashMap<>();

        for(final String property : properties) {
            ontology.getPropertyByPropertyUri(property)
                .ifPresent(p -> {
                    if(p.getType().equalsIgnoreCase("http://www.w3.org/2002/07/owl#ObjectProperty")) {
                        context.put(property, DEFAULT_TYPE_ID_CONTEXT_ENTRY);
                    } else if(p.getRange() != null) {
                        context.put(property, Collections.singletonMap("@type", p.getRange()));
                    }
                });
        }


        return context;
    }
}
