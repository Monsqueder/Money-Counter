package com.moneycounter.counter.repositories;

import com.moneycounter.counter.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
