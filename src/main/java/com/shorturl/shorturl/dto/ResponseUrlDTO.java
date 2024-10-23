package com.shorturl.shorturl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseUrlDTO {
    
    private String originalUrl;
    private String shortenUrl;
    private String alias;

}
