package com.academy.course.appcafe.service;

import com.academy.course.appcafe.dto.DiscountDTO;
import org.springframework.data.domain.Page;

public interface DiscountService {
    void createNewDiscount(DiscountDTO discountDTO);
    void updateDiscount(Long discountId,DiscountDTO newValue);
    DiscountDTO getDiscountById(Long discountId);
    void deleteDiscountById(Long discountId);
    Page<DiscountDTO>getPaginatedListOfDiscounts(int page,int size);
}
