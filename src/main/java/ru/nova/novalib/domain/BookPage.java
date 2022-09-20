package ru.nova.novalib.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class BookPage {

    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "id";
    private String search;
    private int type = 1;
    private Set<Long> genreSet = new HashSet<>();

    public void addGenre(Long genreId){
        genreSet.add(genreId);
    }

    public void clearSet(){
        genreSet.clear();
    }

}
