package com.green.andrii.greenrest.utils;

import com.green.andrii.greenrest.dto.ProductDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientStatusCalculator {
    @Bean
    public String calculate(List<ProductDTO> productDTOList) {
        List<String> statusList = productDTOList.stream().map(productDTO -> productDTO.getStatus()).toList();
        if (statusList.contains("n"))
            return "n";
        if (statusList.contains("u"))
            return "u";
        return "c";
    }
}
