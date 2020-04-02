package dev.uedercardoso.snack.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.apache.cxf.common.util.Base64Utility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import dev.uedercardoso.snack.domain.model.order.Orders;
import dev.uedercardoso.snack.domain.model.order.OrdersDTO;
import dev.uedercardoso.snack.domain.model.person.Person;
import dev.uedercardoso.snack.domain.model.person.PersonDTO;
import dev.uedercardoso.snack.domain.model.snack.Ingredient;
import dev.uedercardoso.snack.domain.model.snack.SnackDTO;
import dev.uedercardoso.snack.domain.model.token.AuthenticationToken;
import dev.uedercardoso.snack.domain.repositories.OrdersRepository;
import dev.uedercardoso.snack.domain.repositories.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SnackWebApplicationTests {
	
	@LocalServerPort
    private int randomServerPort;

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private OrdersRepository orderRepository;

	private static final String DEFAULT_URL = "http://localhost";
	
	private static final String CLIENT_ID = "startup";
	private static final String CLIENT_SECRET = "12345678";
	
	private static final String USERNAME = "admin"; //Usuário cadastrado no banco
	private static final String PASSWORD = "123456"; //Senha cadastrada no banco
	
	private RestTemplate restTemplate;
	
	//Exemplos de testes unitáriso para listas
	
	@Test
    public void testGetAllIngredientsSuccess() throws URISyntaxException 
    {

    	this.restTemplate = new RestTemplate();
        final String baseUrl = DEFAULT_URL + ":" + randomServerPort + "/ingredients";
        URI uri = new URI(baseUrl);
        
        HttpEntity<MultiValueMap<String, String>> entity = this.auth();
        
        ResponseEntity<Ingredient[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, Ingredient[].class);
        List<Ingredient> ingredients =  Arrays.asList(responseEntity.getBody());
        
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(true, ingredients != null && ingredients.size() > 0);
        
    }
    
    @Test
    public void testGetAllOrdersSuccess() throws URISyntaxException 
    {
    	this.restTemplate = new RestTemplate();
        final String baseUrl = DEFAULT_URL + ":" + randomServerPort + "/orders";
        URI uri = new URI(baseUrl);
        
        HttpEntity<MultiValueMap<String, String>> entity = this.auth();
        
        ResponseEntity<OrdersDTO[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, OrdersDTO[].class);
        List<OrdersDTO> ordersDTO =  Arrays.asList(responseEntity.getBody());
        
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(true, ordersDTO != null && ordersDTO.size() > 0);
        
    }
    
    @Test
    public void testGetOrderByIdSuccess() throws URISyntaxException 
    {
    	Long id = 1l;
    	this.restTemplate = new RestTemplate();
    	
    	List<Orders> orders = this.orderRepository.findAll();
    	
    	if(orders != null || orders.size() > 0)
    		id = orders.get(0).getId();
    	
    	final String baseUrl = DEFAULT_URL + ":" + randomServerPort + "/orders/"+id;
        URI uri = new URI(baseUrl);
        
        HttpEntity<MultiValueMap<String, String>> entity = this.auth();
        
        ResponseEntity<OrdersDTO> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, OrdersDTO.class);
        OrdersDTO orderDTO =  responseEntity.getBody();
        
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(true, orderDTO != null);
    }
    
    @Test
    public void testGetAllPersonSuccess() throws URISyntaxException 
    {
    	this.restTemplate = new RestTemplate();
    	final String baseUrl = DEFAULT_URL + ":" + randomServerPort + "/persons";
        URI uri = new URI(baseUrl);
        
        HttpEntity<MultiValueMap<String, String>> entity = this.auth();
        
        ResponseEntity<PersonDTO[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, PersonDTO[].class);
        List<PersonDTO> personDTO =  Arrays.asList(responseEntity.getBody());
        
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(true, personDTO != null && personDTO.size() > 0);
    }
    
    @Test
    public void testGetPersonByIdSuccess() throws URISyntaxException 
    {
    	Long id = 1l;
    	
    	this.restTemplate = new RestTemplate();
    	
    	List<Person> person = this.personRepository.findAll();
    	if(person != null || person.size() > 0)
    		id = person.get(0).getId();
    	
    	final String baseUrl = DEFAULT_URL + ":" + randomServerPort + "/persons/" + id;
        URI uri = new URI(baseUrl);
        
        HttpEntity<MultiValueMap<String, String>> entity = this.auth();
        
        ResponseEntity<PersonDTO> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, PersonDTO.class);
        PersonDTO personDTO =  responseEntity.getBody();
        
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(true, personDTO != null);
    }

    @Test
    public void testGetAllSnackSuccess() throws URISyntaxException 
    {
    	this.restTemplate = new RestTemplate();
    	final String baseUrl = DEFAULT_URL + ":" + randomServerPort + "/snacks";
        URI uri = new URI(baseUrl);
        
        HttpEntity<MultiValueMap<String, String>> entity = this.auth();
        
        ResponseEntity<SnackDTO[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, SnackDTO[].class);
        List<SnackDTO> snackDTO = Arrays.asList(responseEntity.getBody());
        
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(true, snackDTO != null && snackDTO.size() > 0);
    }
    
	
	private HttpEntity<MultiValueMap<String, String>> auth() throws URISyntaxException {
		
        String token = this.authenticate(CLIENT_ID, CLIENT_SECRET, USERNAME, PASSWORD);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+token);
        
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username",USERNAME);
        map.add("password",PASSWORD);
        map.add("grant_type","password");
        
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        
        return entity;
	}
	
    private String authenticate(String clientId, String clientSecret, String username, String password) throws URISyntaxException {
    	RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:" + randomServerPort + "/oauth/token";
        URI uri = new URI(baseUrl);
        
        String userAndPass = clientId + ":" + clientSecret;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic "+Base64Utility.encode(userAndPass.getBytes()));
        
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username",username);
        map.add("password",password);
        map.add("grant_type","password");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        
        ResponseEntity<AuthenticationToken> response = restTemplate.exchange(uri,HttpMethod.POST,entity,AuthenticationToken.class);
        
        AuthenticationToken tokenInfo = response.getBody();
        
        if(response.getStatusCodeValue() == 200)
        	return tokenInfo.getAccess_token();
        
        return "";
    }
    
    /*@Test
    public void testGetAllIngredientsFail() throws URISyntaxException {
    	RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:" + port + "/ingredients";
        URI uri = new URI(baseUrl);
     
        try {
        	restTemplate.getForEntity(uri, String.class);
        	Assert.fail();
        } catch(HttpClientErrorException e) {
        	 Assert.assertEquals(400, e.getRawStatusCode());
        }
    }*/
}
