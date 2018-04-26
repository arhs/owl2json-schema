package com.github.arhs.owl2json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.arhs.owl2json.model.json.JsonSchemaObject;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Ignore
public class ExampleTest {

    private String ONTOLOGY_PATH;
    private String POLICY_PATH;
    private String RDF_WORK_PATH;
    private String JSON_WORK_PATH;

    @Before
    public void setup() throws Exception {
        final ClassLoader classLoader = getClass().getClassLoader();

        this.ONTOLOGY_PATH = classLoader.getResource("ontology/cdm.rdf").getPath();
        this.POLICY_PATH = classLoader.getResource("ontology/ont-policy.rdf").getPath();
        this.RDF_WORK_PATH = classLoader.getResource("JOC_2014_071_R_0020_03.rdf").getPath();
        this.JSON_WORK_PATH = classLoader.getResource("JOC_2014_071_R_0020_03.json").getPath();
    }

    @Test
    public void toJsonSchema() {
        final Owl2Json owl2Json = new Owl2Json(POLICY_PATH, ONTOLOGY_PATH);

        final Model model = ModelFactory.createDefaultModel();
        model.read(RDF_WORK_PATH);

        final List<String> types = Collections.singletonList("http://publications.europa.eu/ontology/cdm#work");
        final Map<String, Object> map = owl2Json.toFormData(model, "iri", types);

        System.out.println(prettify(map));
    }

    @Test
    public void toModel() throws IOException {
        final Owl2Json owl2Json = new Owl2Json(POLICY_PATH, ONTOLOGY_PATH);

        final String jsonModel = readFile(JSON_WORK_PATH);
        final Model model = owl2Json.toModel(jsonModel, "http://publications.europa.eu/resource/genpub/2006.0101a");
        model.write(System.out);
    }

    @Test
    public void toFormData() throws IOException {
        final Owl2Json owl2Json = new Owl2Json(POLICY_PATH, ONTOLOGY_PATH);

        final List<String> classes = Collections.singletonList("http://publications.europa.eu/ontology/cdm#work");
        final JsonSchemaObject jsonTemplate = owl2Json.generateTemplate(classes);

        System.out.println(prettify(jsonTemplate));
    }

    /* Helpers */
    private static String prettify(final Map<String, Object> map) {
        return map.entrySet().stream()
                .map(e -> e.getKey() + " - " + e.getValue())
                .collect(Collectors.joining("\n"));
    }

    private static String prettify(final JsonSchemaObject json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(json);
    }

    private static String readFile(final String path) throws IOException {
        final Path p = Paths.get(path);
        final byte[] bytes = Files.readAllBytes(p);

        return new String(bytes);
    }

}