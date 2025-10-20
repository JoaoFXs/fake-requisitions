This project's README is also available in [Portuguese](https://github.com/JoaoFXs/fake-requisitions/tree/main/utils/README.md).
# Fake Requisitions

Java library for generating HTTP requests with fake (mock) data using [Java Faker](https://github.com/DiUS/java-faker) and sending them to an endpoint configured via Spring Boot.

---

## Description

- Generate multiple JSONs with dynamic fields based on Faker generators, using `Supplier<Object>` for complete flexibility.
  -- Send these JSON requests to a URL configured via Spring Boot properties.
  -- Facilitate testing and populating APIs with mocked data.

---

## Main Dependencies

- Java Faker
- Gson
- Spring Web (RestTemplate)
- Spring Boot Configuration (for custom properties)

---

## Installation

Include the package in your project (via Maven or Gradle), or import the code directly into your Spring Boot project.

```java
<dependency>
<groupId>io.github.joaofxs</groupId>
<artifactId>fake-requisitions</artifactId>
<version><!-- Enter version here --></version>
</dependency>
```

---

## Configuration

Configure the endpoint URL that will receive requests in the `application.properties` (or `application.yml`) file:

```properties
fake.requisitions.url=http://localhost:8080/api/endpoint
```

## Usage

The library allows you to easily create random, dynamic, and scalable JSON payloads to be sent as HTTP POST requests.

Usage example:

```java
@RestController
@RequestMapping("/template")
public class ControllerTest {

private final FakeRequisitions fakeRequisitions; 

@Autowired 
public LaboratorioController(FakeRequisitions fakeRequisitions) { 
this.fakeRequisitions = fakeRequisitions; 
} 

@PostMapping("/generate") 
private ResponseEntity<List<String>> generateTemplate() throws Exception { 
Map<String, Supplier<Object>> fields = new HashMap<>(); 
fields.put("name", () -> fakeRequisitions.educator().secondarySchool()); 
fields.put("email", () -> fakeRequisitions.internet().emailAddress()); 
fields.put("registration", () -> fakeRequisitions.number().numberBetween(1000,9999)); 
List<String> jsons = fakeRequisitions.generateJsons(3, fields); 

fakeRequisitions.sendRequisition(jsons); 
return ResponseEntity.created(URI.create("/template")).body(jsons);
}

}

```
### Generated Json Structure

The above example can generate a JSON like:

``` json
{
"name": "Jessica Luiza Pereira",
"email": "jessica.luiza94@example.com",
"age": 27,
"city": "São Paulo"
}
```

## Details of the main methods

### 1. generateJsons
**Signature:** `generateJsons(int quantity, Map<String, Supplier<Object>> fields)`

Generates a list of JSONs based on the provided fields and data suppliers (Supplier<Object>). When creating the Map field, remember that FakeRequisitions already extends the Faker library, so just include the function () -> array along with the Faker method.
- Parameters:
- quantity: number of JSONs to be generated.
- fields: map of fields for data providers. Each key is the name of the JSON field, and the value is a Supplier that generates the value.
- Returns: List<String> — list of generated JSONs.

Usage example:

```java
Map<String, Supplier<Object>> fields = new HashMap<>();
fields.put("nome", () -> faker.name().fullName());
fields.put("email", () -> faker.internet().emailAddress());

List<String> jsons = fakeRequisitions.generateJsons(5, fields);
```

### 2. sendRequisition
**Signature:** `sendRequisition(List<String> jsons)`

Sends a list of JSONs to the endpoint configured in application.properties.
- Parameters:
- jsons: List of JSONs to be sent via HTTP POST.
- Behavior:
- Sets the Content-Type to application/json.
- Sends each JSON using RestTemplate.
- Prints the request status and server response to the console.

Usage example:

```java
fakeRequisitions.sendRequisition(jsons);
```

### Contribute to the Project

Feel free to open issues and pull requests:
1. Fork the repository.
2. Create a branch for your feature (git checkout -b my-feature).
3. Commit your changes (git commit -m 'Add my feature').
4. Push to the branch (git push origin minha-feature).
5. Open a Pull Request.

### License

[MIT](LICENSE) © JoaoFXs