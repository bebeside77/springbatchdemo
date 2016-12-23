package com.may.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bebeside77
 */
@RestController
public class TestController {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job testJob;

	@RequestMapping("/testJob")
	public String testJob(@RequestParam("key") String key, @RequestParam("value") String value) {
		JobParameters jobParameters = new JobParametersBuilder().addString(key, value).toJobParameters();

		try {
			jobLauncher.run(testJob, jobParameters);
		} catch (Exception e) {
			log.error("", e);

			return "error";
		}

		return "ok";
	}

}
