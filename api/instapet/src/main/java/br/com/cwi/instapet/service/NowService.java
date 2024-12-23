package br.com.cwi.instapet.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NowService {
    public LocalDateTime now(){
        return LocalDateTime.now();
    }
}
