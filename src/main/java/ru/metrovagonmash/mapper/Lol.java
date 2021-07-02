package ru.metrovagonmash.mapper;

import org.springframework.stereotype.Component;

@Component
public class Lol implements Mapper<Long, String> {
    @Override
    public String toDTO(Long aLong) {
        return null;
    }

    @Override
    public Long toModel(String s) {
        return null;
    }
}
