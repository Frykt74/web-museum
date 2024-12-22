package com.web.view.dto.note.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NoteCreateForm(
        @NotNull(message = "Id of equipment cannot be null")
        Integer equipmentId,

        @NotBlank(message = "Fact cannot be empty")
        String fact
) {
}
