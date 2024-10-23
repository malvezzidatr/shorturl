package com.shorturl.shorturl.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shorturl.shorturl.dto.Url;

public interface UrlRepository extends JpaRepository<Url, UUID> {
    Url findByShortenUrl(String shortenUrl);
}
