package com.submitter.val.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 
 * Build a in-memory repository to hold submitter ID values for lookup
 * 
 * @author Devanadha Prabhu
 *
 */
@EnableCaching
@Configuration
public class CaffeineConfig {
	@Bean
	public Caffeine caffeineConfiguration() {
		return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES);
	}

	/**
	 * Setup a submitter enrollment cache for lookups
	 * 
	 * @param caffeine
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(Caffeine caffeine) {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.getCache("submitterID");
		caffeineCacheManager.setCaffeine(caffeine);
		caffeineCacheManager.getCache("submitterID").put("147852", "147852");
		caffeineCacheManager.getCache("submitterID").put("852369", "852369");
		caffeineCacheManager.getCache("submitterID").put("951753", "951753");
		return caffeineCacheManager;
	}
}
