package com.learning.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

	 /* To manage the transactions, we need to implement the interface PlatformTransactionManager, which is implemented
	by class MongoTransactionManager */
	@Bean
	public PlatformTransactionManager manager(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}

}
