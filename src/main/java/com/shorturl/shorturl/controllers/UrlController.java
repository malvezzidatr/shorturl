package com.shorturl.shorturl.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shorturl.shorturl.dto.RequestUrlDTO;
import com.shorturl.shorturl.dto.ResponseUrlDTO;
import com.shorturl.shorturl.services.UrlServiceImpl;

import java.net.URISyntaxException;

import org.slf4j.Logger;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("v1/url")
public class UrlController {
    @Autowired
    private UrlServiceImpl urlService;

    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

    @PostMapping("/shorten")
    public ResponseEntity<ResponseUrlDTO> shortenUrl(@RequestBody RequestUrlDTO url) throws URISyntaxException {
        logger.info("Start - UrlController - shortenUrl - url: {}", url.getUrl());
        ResponseUrlDTO response = urlService.shortenUrl(url);
        logger.info("End - UrlController - shortenUrl: {}, originalUrl: {}", response.getShortenUrl(), response.getOriginalUrl());
        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping()
    public String getUrl(@RequestParam String shortUrl) {
        return shortUrl;
    }
    
}
