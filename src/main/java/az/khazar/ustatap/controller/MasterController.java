package az.khazar.ustatap.controller;

import az.khazar.ustatap.dto.Dto;
import az.khazar.ustatap.service.MasterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/masters")
public class MasterController {

    private final MasterService masterService;

    public MasterController(MasterService masterService) {
        this.masterService = masterService;
    }

    @GetMapping
    public List<Dto.MasterResponse> getAll() {
        return masterService.getAll();
    }

    @GetMapping("/{id}")
    public Dto.MasterResponse getById(@PathVariable Long id) {
        return masterService.getById(id);
    }
}