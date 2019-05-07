package com.example.importawarenoproxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImportAwareNoProxyApplicationTests {

	@Autowired
	private SampleService sampleService;

	@Test
	public void userServiceExists() {
		assertThat(sampleService).isNotNull();
	}

	@Configuration(proxyBeanMethods = false)
	static class NoProxyConfiguration implements ImportAware {

		private AnnotationMetadata annotationMetadata;

		@Override
		public void setImportMetadata(AnnotationMetadata importMetadata) {
			annotationMetadata = importMetadata;
		}

		@Bean
		public SampleService userService() {
			if (annotationMetadata == null) {
				return null;
			} else {
				return new SampleService();
			}
		}
	}


	@Configuration
	@Import(NoProxyConfiguration.class)
	static class BasicConfiguration {
	}

	@Service
	static class SampleService {
	}
}
