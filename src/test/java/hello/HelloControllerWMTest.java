/**
 * 
 */
package hello;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.github.tomakehurst.wiremock.junit.WireMockRule;


/**
 * @author claudiu
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerWMTest {

    private RestTemplate restTemplate;
    private ResponseEntity<String> response;

    private String buildApiMethodUrl() {
        return String.format("http://localhost:%d/Hello/", 8090);
    }
    
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(8090), false);    
    
    @Before
    public void setup() throws Exception {
        restTemplate = new RestTemplate();
        response = null;
    }
    
    @Test
    public void testIndex() {
        wireMockRule.stubFor(get(urlEqualTo("/Hello/"))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("[\"ONE\",\"TWO\",\"THREE\"]"))
        );
 
        response = restTemplate.getForEntity(buildApiMethodUrl(), String.class);
        
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
        assertEquals(response.getBody(), "[\"ONE\",\"TWO\",\"THREE\"]");
    }
 
    @Test
    public void testOne() {
        wireMockRule.stubFor(get(urlEqualTo("/Hello/1"))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("\"ONE\""))
        );
 
        response = restTemplate.getForEntity(buildApiMethodUrl()+"/1", String.class);
        
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
        assertEquals(response.getBody(), "\"ONE\"");
    }
    
    @Test
    public void negativeTestFour() {
        wireMockRule.stubFor(get(urlEqualTo("/Hello/4"))
                .willReturn(aResponse()
                .withStatus(500))
        );
 
        response = restTemplate.getForEntity(buildApiMethodUrl()+"/4", String.class);
        
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  
}

//	@Test
//	public void testIndex() throws Exception {
//		setupStub();
//		assertTrue(template.get)
//		
//	    given().
//	    when().
//	        get("http://localhost:8090/an/endpoint").
//	    then().
//	        assertThat().statusCode(200);
//	    
//	    ResponseEntity<String> response = 
//        		template.getForEntity(new URL("http://localhost:" + port + "/Hello/").toString(), String.class);
//		assertThat(response.getStatusCodeValue(), is(200));
//		assertThat(response.getBody(), is("[\"ONE\",\"TWO\",\"THREE\"]"));
//	}

//    private void setupStub() {
//		stubFor(get(urlEqualTo("/Hello/"))
//				.willReturn(aResponse()
//				.withStatus(200)
//				.withHeader("Content-Type", "application/json")));
//				.withBody("[\"ONE\",\"TWO\",\"THREE\"]")	
//	}


