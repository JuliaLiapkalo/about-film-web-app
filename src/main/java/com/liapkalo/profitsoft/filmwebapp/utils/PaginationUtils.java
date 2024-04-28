package com.liapkalo.profitsoft.filmwebapp.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PaginationUtils {

    @Value("${page-number-for-pagination}")
    public int PAGE_NUMBER;
    @Value("${page-size-for-pagination}")
    public int PAGE_SIZE;

}
