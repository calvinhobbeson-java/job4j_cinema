package ru.job4j.cinema.dto;

import java.util.Map;

public class FilmDto {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "description", "description",
            "/year/", "year",
            "minimal_age", "minimalAge",
            "duration_in_minutes", "durationInMinutes",
            "genre", "genre",
            "image", "image"
    );

    private int id;
    private String name;
    private String description;
    private int year;
    private int minimalAge;
    private int durationInMinutes;
    private String genre;
    private FileDto image;


    public FileDto getImage() {
        return image;
    }

    public void setImage(FileDto image) {
        this.image = image;
    }

    public FilmDto(int id, String name, String description, int year, int minimalAge, int durationInMinutes, String genre, FileDto image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.minimalAge = minimalAge;
        this.durationInMinutes = durationInMinutes;
        this.genre = genre;
        this.image = image;
    }

    public FilmDto(int id, String name, String description, int year, int minimalAge, int durationInMinutes, String genreName) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
