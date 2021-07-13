package ru.metrovagonmash.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.service.ProfileServiceImpl;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileServiceImpl profileService;

    @GetMapping("/")
    public Callable<ResponseEntity<List<Profile>>> findAll() {
        return () -> ResponseEntity.ok(profileService.findAll());
    }

    @PostMapping("/save")
    public Callable<ResponseEntity<Profile>> saveProfile(@RequestBody Profile profile) {
        return () -> ResponseEntity.ok(profileService.save(profile));
    }

    @PutMapping("/update/{id}")
    public Callable<ResponseEntity<Profile>> updateProfile(@RequestBody Profile profile, @PathVariable String id) {
        return () -> ResponseEntity.ok(profileService.update(profile,Long.parseLong(id)));
    }
    @DeleteMapping("/delete/{id}")
    public Callable<ResponseEntity<Profile>> deleteProfile( @PathVariable String id) {
        return () -> ResponseEntity.ok(profileService.deleteById(Long.parseLong(id)));
    }

    @PutMapping("/temp-banned")
    public Callable<ResponseEntity<Profile>> tempBanned(@RequestParam("id") String id,
                                                        @RequestParam("status") String status) {
        return () -> ResponseEntity.ok(profileService.changeAccountNonLocked(Boolean.parseBoolean(status),
                Long.parseLong(id)));
    }
}
