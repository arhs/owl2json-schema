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
package com.github.arhs.owl2json.utils;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.vocabulary.OWL;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;


public class ModelUtilTest {

    private static final Path RDF_WORK_PATH = Paths.get("src/test/resources/work.rdf");

    @Test
    public void resolve0SameAsesFromEmptyModel() {
        final Model model = ModelFactory.createDefaultModel();
        final String iriToResolve = "http://publications.europa.eu/resource/cellar/my_cellar";

        assertTrue(ModelUtil.resolveSameAses(model, iriToResolve).size() == 0);
    }

    @Test
    public void resolve0SameAses() {
        final Model model = ModelFactory.createDefaultModel();
        final Resource resource1 = model.createResource("http://publications.europa.eu/resource/oj/my_oj");
        final Resource resource2 = model.createResource("http://publications.europa.eu/resource/cellar/my_cellar");
        model.add(resource1, OWL.sameAs, resource2);
        final String iriToResolve = "http://publications.europa.eu/resource/cellar/my_cellar_NOTEXISTING";

        assertTrue(ModelUtil.resolveSameAses(model, iriToResolve).size() == 0);
    }

    @Test
    public void resolve1SameAsFromSubject() {
        final Model model = ModelFactory.createDefaultModel();
        final Resource resource1 = model.createResource("http://publications.europa.eu/resource/cellar/my_cellar");
        model.add(resource1, ResourceFactory.createProperty("fake_prop"), "fake_object");
        final String iriToResolve = "http://publications.europa.eu/resource/cellar/my_cellar";

        assertTrue(ModelUtil.resolveSameAses(model, iriToResolve).size() == 1);
    }

    @Test
    public void resolve1SameAsFromObject() {
        final Model model = ModelFactory.createDefaultModel();
        final Resource resource1 = model.createResource("http://publications.europa.eu/resource/cellar/my_cellar");
        final Resource resource2 = model.createResource("http://publications.europa.eu/resource/cellar/other_cellar");
        model.add(resource2, ResourceFactory.createProperty("fake_prop"), resource1);
        final String iriToResolve = "http://publications.europa.eu/resource/cellar/my_cellar";

        assertTrue(ModelUtil.resolveSameAses(model, iriToResolve).size() == 1);
    }

    @Test
    public void resolve1SameAsFromSubjectAndObject() {
        final Model model = ModelFactory.createDefaultModel();
        final Resource resource1 = model.createResource("http://publications.europa.eu/resource/cellar/my_cellar");
        model.add(resource1, ResourceFactory.createProperty("fake_prop"), resource1);
        final String iriToResolve = "http://publications.europa.eu/resource/cellar/my_cellar";

        assertTrue(ModelUtil.resolveSameAses(model, iriToResolve).size() == 1);
    }

    @Test
    public void resolve2SameAses() {
        final Model model = ModelFactory.createDefaultModel();
        final Resource resource1 = model.createResource("http://publications.europa.eu/resource/oj/my_oj");
        final Resource resource2 = model.createResource("http://publications.europa.eu/resource/cellar/my_cellar");
        model.add(resource1, OWL.sameAs, resource2);
        final String iriToResolve = "http://publications.europa.eu/resource/cellar/my_cellar";

        assertTrue(ModelUtil.resolveSameAses(model, iriToResolve).size() == 2);
    }

    @Test
    public void resolve3SameAses() {
        final Model model = ModelFactory.createDefaultModel();
        final Resource resource1 = model.createResource("http://publications.europa.eu/resource/oj/my_oj");
        final Resource resource2 = model.createResource("http://publications.europa.eu/resource/cellar/my_cellar");
        final Resource resource3 = model.createResource("http://publications.europa.eu/resource/celex/my_celex");
        model.add(resource1, OWL.sameAs, resource2);
        model.add(resource1, OWL.sameAs, resource3);
        final String iriToResolve = "http://publications.europa.eu/resource/cellar/my_cellar";

        assertTrue(ModelUtil.resolveSameAses(model, iriToResolve).size() == 3);
    }

    @Test
    public void resolve1SameAsFromReflexiveResources() {
        final Model model = ModelFactory.createDefaultModel();
        final Resource resource1 = model.createResource("http://publications.europa.eu/resource/cellar/my_cellar");
        model.add(resource1, OWL.sameAs, resource1);
        final String iriToResolve = "http://publications.europa.eu/resource/cellar/my_cellar";

        assertTrue(ModelUtil.resolveSameAses(model, iriToResolve).size() == 1);
    }

    @Test
    public void resolve2SameAsesFromReflexiveResources() {
        final Model model = ModelFactory.createDefaultModel();
        final Resource resource1 = model.createResource("http://publications.europa.eu/resource/cellar/my_cellar");
        final Resource resource2 = model.createResource("http://publications.europa.eu/resource/celex/my_celex");
        model.add(resource1, OWL.sameAs, resource1);
        model.add(resource1, OWL.sameAs, resource2);
        final String iriToResolve = "http://publications.europa.eu/resource/cellar/my_cellar";

        assertTrue(ModelUtil.resolveSameAses(model, iriToResolve).size() == 2);
    }

    @Test
    public void writeTest() throws IOException {
        final Model model = readModel(RDF_WORK_PATH);
        final OutputStream os = new ByteArrayOutputStream();

        // write model
        ModelUtil.write(model, os);

        // read model
        final Model resultModel = ModelFactory.createDefaultModel();
        resultModel.read(new ByteArrayInputStream(os.toString().getBytes()), null);

        // compare models
        assertTrue(model.isIsomorphicWith(resultModel));
    }

    @Test
    public void writeNullCaseTest() throws IOException {
        final Model model = readModel(RDF_WORK_PATH);
        final OutputStream os = new ByteArrayOutputStream();

        ModelUtil.write(model, null);
        ModelUtil.write(null, os);
    }

    @Test
    public void addTest() {
        final Model model = ModelFactory.createDefaultModel();
        final Resource resource1 = model.createResource("http://publications.europa.eu/resource/cellar/my_cellar");
        final Resource resource2 = model.createResource("http://publications.europa.eu/resource/celex/my_celex");
        model.add(resource1, OWL.sameAs, resource1);
        model.add(resource1, OWL.sameAs, resource2);

        final Model resultModel = ModelUtil.add(ModelFactory.createDefaultModel(), model, null);

        assertTrue(model.isIsomorphicWith(resultModel));
    }

    @Test
    public void isBlankTest() {
        assertTrue(ModelUtil.isBlank(null));
        assertTrue(ModelUtil.isBlank(ModelFactory.createDefaultModel()));

        final Model model = ModelFactory.createDefaultModel();
        final Resource resource1 = model.createResource("http://publications.europa.eu/resource/cellar/my_cellar");
        final Resource resource2 = model.createResource("http://publications.europa.eu/resource/celex/my_celex");
        model.add(resource1, OWL.sameAs, resource1);
        model.add(resource1, OWL.sameAs, resource2);
        assertFalse(ModelUtil.isBlank(model));
    }

    @Test
    public void testCloseQuietlyMultiple() {
        final List<Model> models = IntStream.range(1, 5)
                .mapToObj(i -> ModelFactory.createDefaultModel())
                .collect(Collectors.toList());
        ModelUtil.closeQuietly(models);
        models.forEach(model -> assertTrue(model.isClosed()));
    }

    @Test
    public void testCloseQuietly() {
        ModelUtil.closeQuietly();
        ModelUtil.closeQuietly((Model[]) null);

        final Model[] models = new Model[4];
        models[0] = null;
        models[1] = ModelFactory.createDefaultModel();
        models[2] = ModelFactory.createDefaultModel();
        models[2].close();
        models[3] = mock(Model.class);
        doThrow(Exception.class).when(models[3]).close();

        ModelUtil.closeQuietly(models);
    }

    /* Helper */
    private static Model readModel(final Path path) throws IOException {
        final Model model = ModelFactory.createDefaultModel();
        final String content = new String(Files.readAllBytes(path), "UTF-8");
        model.read(new StringReader(content), "", Lang.RDFXML.getLabel());

        return model;
    }
}