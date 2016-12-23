package com.may;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 *
 * @author bebeside77
 */
@Component
@StepScope
public class TestProcessor implements ItemProcessor<String, String> {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public String process(String s) throws Exception {
		String upperCase = s.toUpperCase();
		log.info("process : {} -> {}", s, upperCase);

		return upperCase;
	}
}
