package org.springcloud.users.domain.service;

import lombok.RequiredArgsConstructor;
import org.springcloud.users.domain.models.dto.request.UserRequest;
import org.springcloud.users.domain.models.dto.response.UserResponse;
import org.springcloud.users.domain.models.entity.User;
import org.springcloud.users.domain.repository.UserRepository;
import static org.springcloud.users.utils.MappingUtils.*;
import static org.springcloud.users.utils.ValidationUtils.assertUserNotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> findAll() {
        return Optional.of(userRepository.findAll())
                .filter(list -> !list.isEmpty())
                .map(list -> list.stream()
                        .map(u -> mapToClass(u, UserResponse.class))
                        .toList())
                .orElseThrow(() -> new IllegalArgumentException("No se encontraron usuarios"));
    }

    public UserResponse findById(UUID uuid) {
        return getUserResponseById(uuid);
    }

    @Transactional
    public UserResponse create(UserRequest userRequest) {
        userRequest = assertUserNotNull(userRequest, () -> new IllegalArgumentException("El usuario no puede ser nulo"));
        var user = userRepository.save(mapToClass(userRequest, User.class));
        return mapToClass(user, UserResponse.class);
    }

    @Transactional
    public Map<String, String> update(UUID uuid, UserRequest userRequest) {
        var user = getUserById(uuid);
        mapToExistingInstance(userRequest, user);
        userRepository.save(user);
        return Map.of("stats", "Los cambios se han actualizado exitosamente");
    }

    @Transactional
    public Map<String, String> delete(UUID uuid) {
        var user = getUserById(uuid);
        userRepository.delete(user);
        return Map.of("stats", "El usuario ha sido eliminado");
    }

    private UserResponse getUserResponseById(UUID uuid) {
        return userRepository.findById(uuid)
                .map(user -> mapToClass(user, UserResponse.class))
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    private User getUserById(UUID uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

}
