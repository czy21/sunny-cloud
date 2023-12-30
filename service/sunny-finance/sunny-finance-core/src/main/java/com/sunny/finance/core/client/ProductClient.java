package com.sunny.finance.core.client;

import com.sunny.product.api.ProductApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "sunny-product", contextId = "productClient", url = "${sunny.feign.product.url:}")
public interface ProductClient extends ProductApi {
}
