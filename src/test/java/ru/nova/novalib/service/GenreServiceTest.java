package ru.nova.novalib.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nova.novalib.domain.Genre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class GenreServiceTest {

    @Autowired
    private GenreService genreService;

    @Test
    void save() {
//        String str = "Арт Боевик Боевые искусства Вампиры Гарем Гендерная интрига Героическое фэнтези Детектив Дзёсэй Додзинси Драма Игра История Кодомо Комедия Махо-сёдзе Меха Мистика Научная фантастика Повседневность Постапокалиптика Приключения Психология Романтика Самурайский боевик Сверхъестественное Сёдзе Сёдзё-ай Сёнэн Сёнэн-ай Смат Спорт Сэйнэн Сюаньхуа Сянься Трагедия Триллер Ужасы Уся Фантастика Фэнтези Школа Экшн Этти Юри Яой";
//        List<String> strings = Arrays.stream(str.split(" ")).toList();
//        List<String> result= new ArrayList<>();
//        for(int count=0; count<strings.size()-1;) {
//            String x = strings.get(count);
//            if (Character.isLowerCase(strings.get(count+1).charAt(0))){
//                result.add(x + " "+ strings.get(count+1));
//                count++;
//            }else result.add(x);
//            count++;
//        }
//        if (Character.isUpperCase((strings.get(strings.size()-1).charAt(0)))) {
//            result.add(strings.get(strings.size()-1));
//        }
//        for (String title : result) {
//            Genre genre = new Genre();
//            genre.setTitle(title);
//            genreService.save(genre);
//        }
//        result.forEach(System.out::println);
    }
}