package com.may;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

/**
 *
 * @author bebeside77
 */
@Component
@StepScope
public class TestWriter implements ItemWriter<String> {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void write(List<? extends String> list) throws Exception {
		list.forEach((string) -> {
			log.info("write : {}", string);
		});

	}
}
