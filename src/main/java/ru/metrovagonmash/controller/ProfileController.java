package ru.metrovagonmash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.metrovagonmash.service.ProfileServiceImpl;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileServiceImpl profileService;
}
