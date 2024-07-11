package com.rf.elasticSearch.repository;

import com.rf.model.VideoModel;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface VideoRpository extends ElasticsearchRepository<VideoModel,String> {
    @Query("{\"bool\":{\"must\":[{\"match\":{\"lang\":\"?0\"}},{\"match\":{\"area\":\"?1\"}}," +
            "{\"match\":{\"year\":?2}},{\"match\":{\"type\":\"?3\"}}]}}")
    List<VideoModel>findByLangAndAreaAndYearAndType(String lang, String area, String year, String type);
}
