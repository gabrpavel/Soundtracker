package com.soundtracker.backend.model.music;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soundtracker.backend.model.movie.Movie;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "albums")
@Schema(description = "Альбом")
public class Album {

    @Id
    @Schema(description = "ID альбома", example = "1rwMhGTCp0D6CN2XKQjyQt")
    String id;

    @Schema(description = "Имя исполнителя", example = "Justin Hurwitz")
    String artistName;

    @Schema(description = "Ссылка на Spotify", example = "https://open.spotify.com/album/1rwMhGTCp0D6CN2XKQjyQt")
    String spotifyUrl;

    @Schema(description = "Обложка", example = "https://i.scdn.co/image/ab67616d0000b2736fce4a71d3973d3bbb12e8d7")
    String cover;

    @Schema(description = "Название альбома", example = "Whiplash (Original Motion Picture Soundtrack)")
    String name;

    @Schema(description = "Количество треков", example = "24")
    int totalTracks;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "album_tracks",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
    private Set<Track> tracks = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "album", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Movie movie;

    public Album(String id, String artistName, String spotifyUrl, String cover, String name, int totalTracks) {
        this.id = id;
        this.artistName = artistName;
        this.spotifyUrl = spotifyUrl;
        this.cover = cover;
        this.name = name;
        this.totalTracks = totalTracks;
    }

    @PreRemove
    private void preRemove() {
        if (movie != null) {
            movie.setAlbum(null);
        }
    }
}
