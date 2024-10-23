package com.shorturl.shorturl.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shorturl.shorturl.controllers.UrlController;
import com.shorturl.shorturl.dto.RequestUrlDTO;
import com.shorturl.shorturl.dto.ResponseUrlDTO;
import com.shorturl.shorturl.dto.Url;
import com.shorturl.shorturl.repositories.UrlRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;


@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlRepository urlRepository;

    private static final Logger logger = LoggerFactory.getLogger(UrlServiceImpl.class);

    @Override
    public ResponseUrlDTO shortenUrl(RequestUrlDTO url) throws URISyntaxException {
        try {
            logger.info("Start - UrlServiceImpl - shortenUrl - url: {}", url.getUrl());
            String domain = extractDomain(url.getUrl());
            String shortUrl = createShortUrl(domain, url.getAlias());
            ResponseUrlDTO responseUrlDTO = ResponseUrlDTO.builder()
                .originalUrl(url.getUrl())
                .shortenUrl(shortUrl)
                .alias(url.getAlias())
                .build();
            Url dbUrl = Url.builder()
                .originalUrl(responseUrlDTO.getOriginalUrl())
                .shortenUrl(responseUrlDTO.getShortenUrl())
                .alias(responseUrlDTO.getAlias())
                .create_at(new Date())
                .build();
            urlRepository.save(dbUrl);
            logger.info("End - UrlServiceImpl - shortenUrl - url: {}, shortUrl: {}", url.getUrl(), shortUrl);
            return responseUrlDTO;
        } catch (Exception e) {
            logger.error("Error - UrlServiceImpl - shortenUrl - url: {}", url.getUrl(), e);
            throw e;
        }
    }

    private String extractDomain(String url) {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            URI uri = new URI(url);
            return uri.getScheme() + "://" + uri.getHost();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("URL inválida: " + url, e);
        }
    }

    private String createShortUrl(String domain, String alias) {
        return domain + "/" + alias;
    }
    
}
