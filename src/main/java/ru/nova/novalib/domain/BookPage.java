package ru.nova.novalib.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class BookPage {

    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "id";
}
