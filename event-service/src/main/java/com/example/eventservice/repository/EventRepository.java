package com.example.eventservice.repository;

import com.example.eventservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Find all events associated with a specific team.
     *
     * @param teamId The ID of the team.
     * @return A list of Event objects associated with the specified team.
     */
    List<Event> findByTeamId(Long teamId);
}