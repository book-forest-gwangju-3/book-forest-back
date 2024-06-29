package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.commit.Commit;
import com.gwangju3.bookforest.dto.commit.CommitDTO;
import com.gwangju3.bookforest.mapper.CommitMapper;
import com.gwangju3.bookforest.service.CommitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@RestController
@RequiredArgsConstructor
public class CommitController {

    private final CommitService commitService;

    @GetMapping("/commits")
    public TreeMap<LocalDate, List<CommitDTO>> getCommits() {
        List<Commit> allCommits = commitService.findCommitsByUser();
        TreeMap<LocalDate, List<CommitDTO>> result = new TreeMap<>((date1, date2) -> date2.compareTo(date1));

        for (Commit commit : allCommits) {
            LocalDate date = commit.getCreatedAt().toLocalDate();
            if (result.containsKey(date)) {
                result.get(date).add(CommitMapper.toDTO(commit));
            } else {
                List<CommitDTO> commitDTOS = new ArrayList<>();
                commitDTOS.add(CommitMapper.toDTO(commit));
                result.put(date, commitDTOS);
            }
        }

        return result;
    }
}
