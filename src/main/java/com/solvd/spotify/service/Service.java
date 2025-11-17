package com.solvd.spotify.service;

import com.solvd.spotify.domain.repositories.JdbcRepository;

public interface Service<T, ID> extends JdbcRepository<T, ID> {
}
