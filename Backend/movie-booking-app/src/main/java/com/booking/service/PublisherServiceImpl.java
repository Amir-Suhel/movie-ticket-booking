package com.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.booking.model.Movie;

@Service
public class PublisherServiceImpl {
	public static final String topic = "java-course";

	@Autowired
	private KafkaTemplate<String, Movie> temp;

	public KafkaTemplate<String, Movie> getTemp() {
		return temp;
	}

	public void setTemp(Movie movie) {
		System.out.println(movie.toString());
		this.temp.send(topic, String.valueOf(movie.getMovieId()), movie);
	}

	public static String getTopic() {
		return topic;
	}

}
