package io.github.jihch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jihch.pojo.People;
import io.github.jihch.utils.HttpClientUtil;
import io.github.jihch.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ClientApplicationTests {

    @Test
    void contextLoads() {
        RestTemplate restTemplate = new RestTemplate();
        // 第二个参数也控制该方法返回值类型。因为该方法返回值为响应体数据。
        String result = restTemplate.getForObject("http://localhost:8080/demo", String.class);
        System.out.println(result);
    }

    @Test
    void withParam() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://localhost:8080/demo2?name=jihch&age=15", String.class);
        System.out.println(result);
    }

    @Test
    void withParam2() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://localhost:8080/demo2?name={1}&age={2}", String.class, "jihch", 15);
        System.out.println(result);
    }

    @Test
    void withParam3() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "jihch");
        map.put("age", 15);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://localhost:8080/demo2?name={name}&age={age}", String.class, map);
        System.out.println(result);
    }

    @Test
    void withParam4() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://localhost:8080/demo3/jihch/15", String.class);
        System.out.println(result);
    }

    @Test
    void withParam5() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://localhost:8080/demo3/{1}/{2}", String.class, "jihch", 15);
        System.out.println(result);
    }

    @Test
    void withParam6() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "jihch");
        map.put("age", 15);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://localhost:8080/demo3/{name}/{age}", String.class, map);
        System.out.println(result);
    }

    @Test
    void getForEntity() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8080/demo", String.class);
        System.out.println(entity.getBody());
        System.out.println(entity.getStatusCodeValue());
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getHeaders());
        System.out.println(entity.getHeaders().getContentType().toString());
        System.out.println(entity.getHeaders().get("Content-Type").get(0));
    }

    @Test
    void postForObject() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "jihch");
        map.put("age", 15);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject("http://localhost:8080/demo4?name=jihch&age=15", map, String.class);
        System.out.println(result);
    }

    @Test
    void withObject() {
        RestTemplate restTemplate = new RestTemplate();
        People people = restTemplate.getForObject("http://localhost:8080/demo5", People.class);
        System.out.println(people);
        System.out.println(people.getId());
        System.out.println(people.getName());
    }

    /**
     * 接收返回值为 List<People> 这种带泛型的
     */
    @Test
    void withGenericity() {
        RestTemplate restTemplate = new RestTemplate();
        List<People> list = restTemplate.getForObject("http://localhost:8080/demo6", List.class);
        System.out.println(list);
        for (People people : list) {
            System.out.println(people);
        }
    }

    @Test
    void exchange() {
        RestTemplate restTemplate = new RestTemplate();
        // HttpEntity 请求体数据
        // 构造方法参数是请求体内容
        HttpEntity<String> httpEntity = new HttpEntity<>("");
        // 泛型为响应体的类型
        ParameterizedTypeReference<List<People>> parameterizedTypeReference =
                new ParameterizedTypeReference<List<People>>() {
        };
        ResponseEntity<List<People>> result = restTemplate.exchange("http://localhost:8080/demo6", HttpMethod.GET,
                null, parameterizedTypeReference);
        List<People> list = result.getBody();
        for (People people : list) {
            System.out.println(people);
        }
    }

    @Test
    void httpClientGet() {
        String result = HttpClientUtil.doGet("http://localhost:8080/demo");
        System.out.println(result);

        result = HttpClientUtil.doGet("http://localhost:8080/demo5");
        System.out.println(result);
        ObjectMapper objectMapper = new ObjectMapper();
        People people = null;
        try {
            people = objectMapper.readValue(result, People.class);
            System.out.println(people);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        result = HttpClientUtil.doGet("http://localhost:8080/demo2?name=jihch&age=15");
        System.out.println(result);

        result = HttpClientUtil.doGet("http://localhost:8080/demo6");
        System.out.println(result);
        List<People> list = JsonUtils.jsonToList(result, People.class);
        System.out.println(list);
        for (People p : list) {
            System.out.println(p);
        }

        Map<String, String> map = new HashMap<>();
        map.put("name", "jihch");
        map.put("age", "15");
        result = HttpClientUtil.doGet("http://localhost:8080/demo2", map);
        System.out.println(result);
    }

}
