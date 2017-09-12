package ftn.upp.app.util;

import java.io.File;
import java.io.IOException;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@Configuration
public class Conf {
	
	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/api/*");
		
		return registrationBean;
	}
	
	@Bean
	NodeBuilder nodeBuilder() {
		return new NodeBuilder();
	}
	 
	@Bean
	ElasticsearchOperations elasticsearchTemplate() {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("temp-elastic", Long.toString(System.nanoTime()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Settings.Builder elasticSearchSettings = 
				Settings.settingsBuilder()
				.put("http.enabled", "true")
				.put("index.number_of_shards", "1")
				.put("path.data", new File(tempFile, "data").getAbsolutePath())
				.put("path.logs", new File(tempFile, "logs").getAbsolutePath())
				.put("path.work", new File(tempFile, "work").getAbsolutePath())
				.put("path.home", tempFile);
				  
		return new ElasticsearchTemplate(nodeBuilder()
				.local(true)
				.settings(elasticSearchSettings)
				.node()
				.client());
	}
}
