package th.co.bookstore.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
	
	@SuppressWarnings("unchecked")
	public List<RestBookResponse> callRestBookApi() throws Exception {
		RestBookResponse[] response = new RestBookResponse[] {};
		try {
			RestTemplate restTemplate = new RestTemplate();
			long startTime = System.currentTimeMillis();
			
			log.info("callRestBookApi Url : {}", this.restBookUrl);
		    HttpEntity<RestBookResponse[]> responseEntity = restTemplate.exchange(this.restBookUrl, HttpMethod.GET, null, RestBookResponse[].class);
			response = responseEntity.getBody();

			long endTime = System.currentTimeMillis();
			log.info("callRestBookApi Completed Response Body  : {} Time : {} ms", responseEntity.getBody(), (endTime - startTime));
		} catch(HttpClientErrorException ehttp) {
			log.error("is ok ehttp status : {}, body : {} ", ehttp.getStatusCode(), ehttp.getResponseBodyAsString());
			throw ehttp;
		} catch (Exception e) {
			log.error("exception callRestBookApi : ", e);
			throw e;
		}
		return CollectionUtils.arrayToList(response);
	}
	
	@SuppressWarnings("unchecked")
	public List<RestBookResponse> callRestBookRecommendationApi() throws Exception {
		RestBookResponse[] response = new RestBookResponse[] {};
		try {
			RestTemplate restTemplate = new RestTemplate();
			long startTime = System.currentTimeMillis();
			
			log.info("callRestBookRecommendationApi Url : {}", this.restBookRecommonendUrl);
		    HttpEntity<RestBookResponse[]> responseEntity = restTemplate.exchange(this.restBookRecommonendUrl, HttpMethod.GET, null, RestBookResponse[].class);
			response = responseEntity.getBody();

			long endTime = System.currentTimeMillis();
			log.info("callRestBookRecommendationApi Completed Response Body  : {} Time : {} ms", responseEntity.getBody(), (endTime - startTime));
		} catch(HttpClientErrorException ehttp) {
			log.error("is ok ehttp status : {}, body : {} ", ehttp.getStatusCode(), ehttp.getResponseBodyAsString());
			throw ehttp;
		} catch (Exception e) {
			log.error("exception callRestBookApi : ", e);
			throw e;
		}
		return CollectionUtils.arrayToList(response);
	}
	
}
