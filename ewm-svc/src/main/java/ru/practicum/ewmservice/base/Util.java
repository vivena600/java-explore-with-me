package ru.practicum.ewmservice.base;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Util {

    public PageRequest createdPageAsc(int from, int size, String sortBy) {
        return PageRequest.of(from > 0 ? from/size : 0, size, Sort.by(sortBy).ascending());
    }

    public PageRequest createdPageDesc(int from, int size, String sortBy) {
        return PageRequest.of(from > 0 ? from/size : 0, size, Sort.by(sortBy).descending());
    }
}
