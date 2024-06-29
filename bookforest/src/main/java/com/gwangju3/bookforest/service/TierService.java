package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.resository.TierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TierService {

    private final TierRepository tierRepository;


}
