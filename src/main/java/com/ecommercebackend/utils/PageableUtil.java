package com.ecommercebackend.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ecommercebackend.exception.BadRequestException;

@Component
public class PageableUtil {

    public Pageable getPageable(int offset, int limit) {
        return PageRequest.of(offset, limit);
    }

    public Pageable getPageable(int offset, int limit, String sortBase, String sortType) throws BadRequestException {
        if (sortType.equals("ASC")) {
            return PageRequest.of(offset, limit, Sort.by(sortBase).ascending());
        }
        if (sortType.equals("DESC")) {
            return PageRequest.of(offset, limit, Sort.by(sortBase).descending());
        }
        throw new BadRequestException("Invalid sort type");
    }

}
