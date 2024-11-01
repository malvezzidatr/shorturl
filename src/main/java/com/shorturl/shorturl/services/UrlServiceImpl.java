package com.shorturl.shorturl.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.shorturl.shorturl.dto.RequestUrlDTO;
import com.shorturl.shorturl.dto.ResponseUrlDTO;
import com.shorturl.shorturl.dto.Url;
import com.shorturl.shorturl.exceptions.AliasAlreadyUsedException;
import com.shorturl.shorturl.exceptions.ExtractDomainException;
import com.shorturl.shorturl.exceptions.UrlNotFoundException;
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
            if (isAliasExists(url.getAlias())) {
                logger.info("End - UrlServiceImpl - method: shortenUrl - alias already exists - url: {}, alias: {}", url.getUrl(), url.getAlias());
                throw new AliasAlreadyUsedException("ALIAS_IN_USE", "Alias already in use");
            }
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
            logger.info("End - UrlServiceImpl - method: shortenUrl - url: {}, shortenUrl: {}", url.getUrl(), shortUrl);
            return responseUrlDTO;
        } catch (AliasAlreadyUsedException ex) {
            logger.error("Error - UrlServiceImpl - method: shortenUrl - url: {}", url.getUrl(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    @Cacheable(value = "shortenUrl", key = "#shortUrl")
    public ResponseUrlDTO getShortenUrl(String shortUrl) throws UrlNotFoundException {
        try {
            logger.info("Start - UrlServiceImpl - method: getShortenUrl - shortUrl: {}", shortUrl);
            Url dbUrl = urlRepository.findByShortenUrl(shortUrl);
            if (dbUrl == null) {
                logger.info("End - UrlServiceImpl - method: getShortenUrl - shorten url is not found - shortUrl: {}", shortUrl);
                throw new UrlNotFoundException("URL_NOT_FOUND", "URL not found");
            }
            ResponseUrlDTO responseUrlDTO = ResponseUrlDTO.builder()
                                                .originalUrl(dbUrl.getOriginalUrl())
                                                .shortenUrl(dbUrl.getShortenUrl())
                                                .alias(dbUrl.getAlias())
                                                .build();
            return responseUrlDTO;
        } catch (UrlNotFoundException ex) {
            logger.error("Error - UrlServiceImpl - method: getShortenUrl - shortUrl: {}", shortUrl, ex.getMessage());
            throw ex;
        }
    }

    private String extractDomain(String url) {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            URI uri = new URI(url);
            return uri.getScheme() + "://" + uri.getHost();
        } catch (URISyntaxException ex) {
            throw new ExtractDomainException("EXTRACT_DOMAIN_ERROR", "Error extracting domain");
        }
    }

    private String createShortUrl(String domain, String alias) {
        return domain + "/" + alias;
    }

    private Boolean isAliasExists(String alias) {
        Url urlExists = urlRepository.findByAlias(alias);
        return urlExists != null;
    }
    
}
