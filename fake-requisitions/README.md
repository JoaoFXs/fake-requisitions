# Fake Requisitions

Biblioteca Java para gerar requisições HTTP com dados falsos (mockados) utilizando [Java Faker](https://github.com/DiUS/java-faker) e enviar para um endpoint configurado via Spring Boot.

---

## Descrição


- Gerar múltiplos JSONs com campos dinâmicos baseados em geradores Faker, usando `Supplier<Object>` para flexibilidade total.
- Enviar essas requisições JSON para uma URL configurada via propriedades do Spring Boot.
- Facilitar testes e populamento de APIs com dados mockados.

---

## Dependências principais

- Java Faker
- Gson
- Spring Web (RestTemplate)
- Spring Boot Configuration (para propriedades customizadas)

---

## Instalação

Inclua o package no seu projeto (via Maven ou Gradle), ou importe o código diretamente no seu projeto Spring Boot.

```java
<dependency>
  <groupId>io.github.joaofxs</groupId>
  <artifactId>fake-requisitions</artifactId>
  <version><!-- Insira a versão aqui --></version>
</dependency>
```

---

## Configuração

Configure a URL do endpoint que receberá as requisições no arquivo `application.properties` (ou `application.yml`):

```properties
fake.requisitions.url=http://localhost:8080/api/endpoint
```

## Uso

A biblioteca permite criar facilmente payloads JSON aleatórios, dinâmicos e escaláveis para serem enviados como requisições HTTP POST.

Exemplo de uso:

```java
@RestController
@RequestMapping("/template")
public class ControllerTest {


    private  final FakeRequisitions fakeRequisitions;
    
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
### Estrutura do Json Gerado

O exemplo acima pode gerar um JSON como:

``` json
{
"nome": "Jessica Luiza Pereira",
"email": "jessica.luiza94@example.com",
"idade": 27,
"cidade": "São Paulo"
}
```

## Detalhamento dos principais métodos

### 1. generateJsons
**Assinatura:** `generateJsons(int quantidade, Map<String, Supplier<Object>> campos)`

Gera uma lista de JSONs com base nos campos fornecidos e em fornecedores de dados (Supplier<Object>). Ao criar o field, do Map, lembre-se que FakeRequisitions ja extende a biblioteca Faker, então, apenas inclua o array function () ->, junto com o método do Faker.
- Parâmetros:
    - quantidade: número de JSONs a serem gerados.
    - campos: mapa de campos para fornecedores de dados. Cada chave é o nome do campo do JSON e o valor é um Supplier que gera o valor.
- Retorno: List<String> — lista de JSONs gerados.

Exemplo de uso:


```java
Map<String, Supplier<Object>> fields = new HashMap<>();
fields.put("nome", () -> faker.name().fullName());
        fields.put("email", () -> faker.internet().emailAddress());

List<String> jsons = fakeRequisitions.generateJsons(5, fields);
```

### 2. sendRequisition
**Assinatura:** `sendRequisition(List<String> jsons)`

Envia uma lista de JSONs para o endpoint configurado no application.properties.
- Parâmetros:
  - jsons: lista de JSONs que serão enviados via HTTP POST.
- Comportamento:
  - Define o Content-Type como application/json.
  - Envia cada JSON usando RestTemplate.
  - Imprime no console o status da requisição e a resposta do servidor.
  
 Exemplo de uso:

```java
fakeRequisitions.sendRequisition(jsons);
```

### Contribua com o Projeto

Sinta-se à vontade para abrir issues e pull requests:
1. Fork o repositório.
2. Crie uma branch para sua feature (git checkout -b minha-feature).
3. Commit suas alterações (git commit -m 'Adiciona minha feature').
4. Push para a branch (git push origin minha-feature).
5. Abra um Pull Request.

### Licença

[MIT](LICENSE) © JoaoFXs