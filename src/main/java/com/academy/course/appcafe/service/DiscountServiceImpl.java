package com.academy.course.appcafe.service;

import com.academy.course.appcafe.converter.DiscountConverter;
import com.academy.course.appcafe.dto.DiscountDTO;
import com.academy.course.appcafe.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountServiceImpl implements DiscountService{

    private final DiscountRepository discountRepository;
    private final DiscountConverter discountConverter;
    @Override
    public void createNewDiscount(DiscountDTO discountDTO) {
        discountRepository.save(discountConverter.toDiscountEntity(discountDTO));
    }

    @Override
    public void updateDiscount(Long discountId, DiscountDTO newValue) {
        if (discountRepository.existsById(discountId)) {
            Discount discount = discountRepository.getReferenceById(discountId);
            discount.setPercentOfDiscount(newValue.getPercentOfDiscount());
            discount.setName(newValue.getName());
            discount.setIsActive(newValue.getIsActive());
            discountRepository.save(discount);
        }
    }

    @Override
    public DiscountDTO getDiscountById(Long discountId) {
        if (discountRepository.existsById(discountId)) {
         return discountConverter.toDiscountDto(discountRepository.getReferenceById(discountId));
        }
        return null;
    }

    @Override
    public void deleteDiscountById(Long discountId) {
        if (discountRepository.existsById(discountId)) {
            discountRepository.deleteById(discountId);
        }
    }

    @Override
    public Page<DiscountDTO> getPaginatedListOfDiscounts(int page, int size) {
        if (page < 0 || size < 1) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Discount> discountPagepage = discountRepository.findAll(pageable);
        List<DiscountDTO> discountDTOS = discountPagepage.getContent().stream()
                .map(discountConverter::toDiscountDto)
                .toList();

        return new PageImpl<>(discountDTOS, pageable, discountPagepage.getTotalElements());
    }
}
