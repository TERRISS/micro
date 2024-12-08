package org.example.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface UserClient {
    @GetExchange("/users")
    boolean isExists(@RequestParam Long userId);
    default boolean fallbackMethod(Long userId, Throwable throwable) {
        return false;
    }
}
