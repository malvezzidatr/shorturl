package com.shorturl.shorturl.services;

import com.shorturl.shorturl.dto.RequestUrlDTO;
import com.shorturl.shorturl.dto.ResponseUrlDTO;
import java.net.URISyntaxException;

public interface UrlService {

    ResponseUrlDTO shortenUrl(RequestUrlDTO url) throws URISyntaxException;
    ResponseUrlDTO getShortenUrl(String shortUrl);

}
