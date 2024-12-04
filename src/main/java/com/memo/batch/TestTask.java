package com.memo.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestTask {

	// 1분마다 한 번씩 수행(batch schedule : 1분)
	@Scheduled(cron = "0 */1 * * * * ")
	public void testJob() { // batch job
		log.info("##### job 수행 #####");
	}
}
