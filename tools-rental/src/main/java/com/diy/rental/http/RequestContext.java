package com.diy.rental.http;

import jakarta.annotation.ManagedBean;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;

@Data
@RequestScope
@ManagedBean
public class RequestContext<T> {
    private T body;
}
