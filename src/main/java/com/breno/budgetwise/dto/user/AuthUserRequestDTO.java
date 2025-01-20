package com.breno.budgetwise.dto.user;

import java.util.Optional;

public record AuthUserRequestDTO(Optional<String> username, Optional<String> email, String password) {
}
