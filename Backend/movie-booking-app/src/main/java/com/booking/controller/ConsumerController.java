package com.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.booking.model.UserDto;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/call/consumer")
@Log4j2
@CrossOrigin(origins = "http://localhost:4200")
public class ConsumerController {
	
	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/login")
	public ResponseEntity<?> consumeLogin(@RequestBody UserDto userdto) throws RestClientException, Exception {
		String baseUrl = "http://localhost:8080/api/auth/signIn";// API consumption.. actual api is hidden -not exposed

		//RestTemplate restTemplate = new RestTemplate();

//		ResponseEntity<Map<String, String>> result;
//
//		try { 
//			result = restTemplate.exchange(baseUrl, HttpMethod.POST, getHeaders(userdto),
//					new ParameterizedTypeReference<Map<String, String>>() {
//					});
//		} catch (Exception e) {
//			return new ResponseEntity<String>("Login failed", HttpStatus.UNAUTHORIZED);
//		}
//
//		return new ResponseEntity<Map<String, String>>(result.getBody(), HttpStatus.OK);
//	}
//
//	private static HttpEntity<UserDto> getHeaders(UserDto userdto) {
//		HttpHeaders headers = new HttpHeaders();
//
//		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
//		return new HttpEntity<UserDto>(userdto, headers);
//	}


		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<UserDto> requestEntity = new HttpEntity<>(userdto, httpHeaders);
		ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, String.class);

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
		    String token = responseEntity.getBody();
		    log.info("token: {}", token);
		} else {
		    System.out.println("Fail");
		}

		return responseEntity;
	}

}
