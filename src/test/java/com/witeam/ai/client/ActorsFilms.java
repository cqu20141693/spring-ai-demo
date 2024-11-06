package com.witeam.ai.client;

import lombok.Data;

import java.util.List;

/**
 * @author weTeam
 * @since 2024/11/5
 **/
@Data
public class ActorsFilms {
    private String actor;

    private List<String> movies;
}
