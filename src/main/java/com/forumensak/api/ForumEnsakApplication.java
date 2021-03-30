package com.forumensak.api;


import com.forumensak.api.service.FilesStorageServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;


@SpringBootApplication
@EnableAsync
public class ForumEnsakApplication {
	@Resource
	FilesStorageServiceImpl storageService;
	public static void main(String[] args) {
		SpringApplication.run(ForumEnsakApplication.class, args);
	}

}
