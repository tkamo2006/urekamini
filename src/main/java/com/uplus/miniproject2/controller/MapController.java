package com.uplus.miniproject2.controller;

import com.uplus.miniproject2.entity.proflie.Profile;
import com.uplus.miniproject2.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class MapController {

    private final MapService mapService;

    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping
    public List<Profile> getAllProfiles() {
        return mapService.findAllProfiles();
    }
}
