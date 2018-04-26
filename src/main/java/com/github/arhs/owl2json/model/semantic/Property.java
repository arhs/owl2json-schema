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

public class Property {

    private String uri;
    private String type;
    private String label;
    private String domain;
    private String range;
    private Integer exactCardinality;
    private Integer minimumCardinality;
    private Integer maximumCardinality;

    public Property() {}

    public Property(String uri, String type, String label, String domain, String range, Integer exactCardinality, Integer minimumCardinality, Integer maximumCardinality) {
        this.uri = uri;
        this.type = type;
        this.label = label;
        this.domain = domain;
        this.range = range;
        this.exactCardinality = exactCardinality;
        this.minimumCardinality = minimumCardinality;
        this.maximumCardinality = maximumCardinality;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }

    public String getRange() {
        return range;
    }

    public void setRange(final String range) {
        this.range = range;
    }

    public Integer getExactCardinality() {
        return exactCardinality;
    }

    public void setExactCardinality(final Integer exactCardinality) {
        this.exactCardinality = exactCardinality;
    }

    public Integer getMinimumCardinality() {
        return minimumCardinality;
    }

    public void setMinimumCardinality(final Integer minimumCardinality) {
        this.minimumCardinality = minimumCardinality;
    }

    public Integer getMaximumCardinality() {
        return maximumCardinality;
    }

    public void setMaximumCardinality(final Integer maximumCardinality) {
        this.maximumCardinality = maximumCardinality;
    }

    @Override
    public String toString() {
        return "Property{" +
                "uri='" + uri + '\'' +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                ", domain='" + domain + '\'' +
                ", range='" + range + '\'' +
                ", exactCardinality=" + exactCardinality +
                ", minimumCardinality=" + minimumCardinality +
                ", maximumCardinality=" + maximumCardinality +
                '}';
    }
}
