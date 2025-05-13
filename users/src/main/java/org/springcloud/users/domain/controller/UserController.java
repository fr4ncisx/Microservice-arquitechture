package org.springcloud.users.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springcloud.users.domain.models.dto.request.UserRequest;
import org.springcloud.users.domain.models.dto.response.UserResponse;
import org.springcloud.users.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("{uuid}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID uuid){
        return ResponseEntity.ok(userService.findById(uuid));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest){
        return ResponseEntity.ok(userService.create(userRequest));
    }

    @PutMapping("{uuid}")
    public ResponseEntity<Map<String, String>> updateUser(@PathVariable UUID uuid, @RequestBody @Valid UserRequest userRequest){
        return ResponseEntity.ok(userService.update(uuid, userRequest));

    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable UUID uuid){
        return ResponseEntity.ok(userService.delete(uuid));
    }

}
