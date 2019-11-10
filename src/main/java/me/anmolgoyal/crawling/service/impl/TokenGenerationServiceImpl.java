package me.anmolgoyal.crawling.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import me.anmolgoyal.crawling.service.TokenGenerationService;

@Service
public class TokenGenerationServiceImpl implements TokenGenerationService {

	@Override
	public String generateToken() {
		return UUID.randomUUID().toString();
	}

}
