package com.soundtracker.backend.model.music;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tracks")
@Schema(description = "Трек")
public class Track {

    @Id
    @Schema(description = "ID трека", example = "2Xtsv7BUMrNodQWH2JPOc0")
    String id;

    @Schema(description = "Имя исполнителя", example = "Justin Hurwitz")
    String artistName;

    @Schema(description = "Ссылка на Spotify", example = "https://open.spotify.com/track/2Xtsv7BUMrNodQWH2JPOc0")
    String spotifyUrl;

    @Schema(description = "Название трека", example = "Caravan")
    String name;

    @Schema(description = "URL-адрес 30-секундного предварительного прослушивания (формат MP3) трека.",
            example = "https://p.scdn.co/mp3-preview/fc57a435e090bc640edc4fadb016ee0fc9d00778?cid=a9dd92e9bff24736a8e0e29dd99aead6")
    String previewUrl;

    @Schema(description = "Номер трека", example = "6")
    int trackNumber;

    @JsonIgnore
    @ManyToMany(mappedBy = "tracks",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Set<Album> albums = new HashSet<>();
}
