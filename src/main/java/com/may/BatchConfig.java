package com.may;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author bebeside77
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private TestProcessor testProcessor;

	@Autowired
	private TestWriter testWriter;

	@Bean
	@StepScope
	public FlatFileItemReader flatFileItemReader() {
		FlatFileItemReader<String> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("test.txt"));
		reader.setLineMapper(new PassThroughLineMapper());
		return reader;
	}

	@Bean
	@StepScope
	public ItemReader flatFileItemListReader() {
		return () -> {
			BufferedReader br = new BufferedReader(
				new FileReader(new ClassPathResource("test.txt").getFile().getAbsolutePath()));

			List<String> lines = new ArrayList<>();

			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}

				lines.add(line);
			}

			return lines;
		};
	}

	@Bean
	public Step testStep() {
		return stepBuilderFactory.get("testStep")
			.<String, String>chunk(2)
			.reader(flatFileItemReader())
			.processor(testProcessor)
			.writer(testWriter)
			.build();
	}

	@Bean
	public Job testJob() {
		return jobBuilderFactory.get("testJob")
			.start(testStep())
			.build();
	}


}
