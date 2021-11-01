package ru.metrovagonmash.config.search;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.metrovagonmash.specification.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Data
public class Search {

    public List<SearchCriteria> getParamsFromSearch (String search) {
        List<SearchCriteria> params = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?|.*?),", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        return params;
    }
}
