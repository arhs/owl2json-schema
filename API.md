# API
Owl2Json allow you tp transform RDF models to Json Schemas and conversely.

## Getting started
Create an instance of `Owl2Json`

```
Owl2Json owl2Json = new Owl2Json("ontology.rdf", "policy.rdf");
```

Arguments:
 * the path to the [OWL](https://www.w3.org/OWL/) ontology
 * the path to the [ontology policy](https://jena.apache.org/documentation/ontology/#document-manager-policy)

## Owl ontology to JSON schema
Generate a [JSON schema](http://json-schema.org/) for one or more classes defined in the ontology
```
JsonSchemaObject json = owl2Json.generateTemplate(types);
```

Arguments:
* the list of class IRIs

Example ([CDM ontology](http://publications.europa.eu/mdr/cdm/index.html)):
```
List<String> types = Collections.singletonList("http://publications.europa.eu/ontology/cdm#work");
JsonSchemaObject json = owl2Json.generateTemplate(types);
```

Result:
```
{
  "type" : "object",
  "title" : "[http://publications.europa.eu/ontology/cdm#work]",
  "properties" : {
    ...
    "http://publications.europa.eu/ontology/cdm#work_age" : {
      "type" : "array",
      "title" : "Age",
      "items" : {
        "type" : "string"
      }
    },
    ...
    "required" : [ "http://publications.europa.eu/ontology/cdm#work_date_document" ]
}
```

## JSON schema to model
Convert a JSON data to a Jena model
```
Model model = owl2Json.toModel(json, identifier);
```

Arguments:
* json, the JSON to convert as a string
* identifier, the identifier of the resource represented by the json

Example:
```
{
  "@id" : "http://publications.europa.eu/resource/oj/JOC_2014_071_R_0020_03",
  "@type" : [ "http://publications.europa.eu/ontology/cdm#work", "http://publications.europa.eu/ontology/cdm#publication_general" ],
  "http://publications.europa.eu/ontology/cdm#date_creation_legacy" : {
    "@type" : "http://www.w3.org/2001/XMLSchema#date",
    "@value" : "2018-03-08"
  },
  ...
}
```

```
String jsonModel = readFile("src/test/resources/JOC_2014_071_R_0020_03.json");
Model model = owl2Json.toModel(jsonModel, "http://publications.europa.eu/resource/genpub/2006.0101a");
```

## Model to form data
Convert a Jena model (of a given subject) to form data based on an Owl Ontology. 
It consists in extracting the triples from the model and returning them as a map of predicates and objects.
```
Map<String, Object> formData = owl2Json.toFormData(model, iri, types);
```

Example:
```
Model model = ModelFactory.createDefaultModel();
model.read("http://publications.europa.eu/resource/celex/62013TA0174");

List<String> types = Collections.singletonList("http://publications.europa.eu/ontology/cdm#work");
Map<String, Object> formData = owl2Json.toFormData(model, "iri", types);
```

Result:
```
@type - [http://publications.europa.eu/ontology/cdm#work]
http://www.w3.org/2002/07/owl#sameAs - []
http://publications.europa.eu/ontology/cdm#work_date_document - 2014-01-23
http://publications.europa.eu/ontology/cdm#published_in - [http://publications.europa.eu/resource/oj/JOC_2014_071_R]
...
```

Arguments:
* model, the Jena model to convert
* iri, the resource [IRI](https://www.w3.org/International/iri-edit/draft-duerst-iri.html) used to resolve the sameAses
* types, the list of ontology classes used to annotate the property `@type`


___________________________

The examples can be found in `src/test/java/com/github/arhs/owl2json/ExampleTest.java`

