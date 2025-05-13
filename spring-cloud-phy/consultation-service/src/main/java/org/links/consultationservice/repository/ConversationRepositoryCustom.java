package org.links.consultationservice.repository;

import org.links.consultationservice.dto.ConversationSearchResult;

import java.util.List;

public interface ConversationRepositoryCustom {
    List<ConversationSearchResult> searchByKeywords(List<String> keywords, Long userId);
}

