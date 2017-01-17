package com.boris.userservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.boris.userservice.TestConfig.MongoTestConfig;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@SpringBootApplication
@Import({MongoTestConfig.class})
public class TestConfig {
	
	@Configuration
	class MongoTestConfig extends AbstractMongoConfiguration {

		@Override
		protected String getDatabaseName() {
			return "test";
		}

		@Override
		public Mongo mongo() throws Exception {
			return new MongoClient("127.0.0.1", 27017);
		}

		@Override
		protected String getMappingBasePackage() {
			return "com.boris.reservations.dao";
		}
	}
}
