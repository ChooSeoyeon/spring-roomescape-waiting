package roomescape.service.theme.dto;

import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeName;

public class ThemeRequest {
    private final String name;
    private final String description;
    private final String thumbnail;

    public ThemeRequest(String name, String description, String thumbnail) {
        validate(name);
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    private void validate(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    public Theme toTheme() {
        return new Theme(new ThemeName(name), description, thumbnail);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
