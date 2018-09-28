package th.co.bookstore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import th.co.bookstore.model.RestBookResponse;

@Service
public class RestApiBookService {

	private static final Logger log = LoggerFactory.getLogger(RestApiBookService.class);
	
	@Value("${rest.api.book.url}")
	private String restBookUrl;
	
	@Value("${rest.api.book.recommendation.url}")
	private String restBookRecommonendUrl;
	
	public RestBookResponse callRestBookApi() throws Exception {
		RestBookResponse response = new RestBookResponse();
		try {
			RestTemplate restTemplate = new RestTemplate(this.getClientHttpRequestFactory());
			long startTime = System.currentTimeMillis();
			
			log.info("callRestBookApi Url : {}", this.restBookUrl);
		    HttpEntity<RestBookResponse> responseEntity = restTemplate.exchange(this.restBookUrl, HttpMethod.GET, null,RestBookResponse.class);
			response = responseEntity.getBody();

			long endTime = System.currentTimeMillis();
			log.info("callRestBookApi Completed Response Body  : {} Time : {} ms", response.toString(), (endTime - startTime));
		} catch(HttpClientErrorException ehttp) {
			log.error("is ok ehttp status : {}, body : {} ", ehttp.getStatusCode(), ehttp.getResponseBodyAsString());
			throw ehttp;
		} catch (Exception e) {
			log.error("exception callRestBookApi : ", e);
			throw e;
		}
		return response;
	}
	
	public RestBookResponse callRestBookRecommendationApi() throws Exception {
		RestBookResponse response = new RestBookResponse();
		try {
			RestTemplate restTemplate = new RestTemplate(this.getClientHttpRequestFactory());
			long startTime = System.currentTimeMillis();
			
			log.info("callRestBookRecommendationApi Url : {}", this.restBookRecommonendUrl);
		    HttpEntity<RestBookResponse> responseEntity = restTemplate.exchange(this.restBookRecommonendUrl, HttpMethod.GET, null,RestBookResponse.class);
			response = responseEntity.getBody();

			long endTime = System.currentTimeMillis();
			log.info("callRestBookRecommendationApi Completed Response Body  : {} Time : {} ms", response.toString(), (endTime - startTime));
		} catch(HttpClientErrorException ehttp) {
			log.error("is ok ehttp status : {}, body : {} ", ehttp.getStatusCode(), ehttp.getResponseBodyAsString());
			throw ehttp;
		} catch (Exception e) {
			log.error("exception callRestBookApi : ", e);
			throw e;
		}
		return response;
	}
	
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
	    int timeout = 10000;
	    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
	    clientHttpRequestFactory.setConnectTimeout(timeout);
	    return clientHttpRequestFactory;
	}
}
