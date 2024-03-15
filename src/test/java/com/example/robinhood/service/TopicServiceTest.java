package com.example.robinhood.service;

import com.example.robinhood.entity.Topic;
import com.example.robinhood.entity.TopicDetail;
import com.example.robinhood.entity.User;
import com.example.robinhood.model.request.AddTopicRequestModel;
import com.example.robinhood.model.request.EditDataTopicRequestModel;
import com.example.robinhood.model.response.GetDataTopicResponseModel;
import com.example.robinhood.model.response.GetTopicHistoryResponseModel;
import com.example.robinhood.repository.TopicRepository;
import com.example.robinhood.service.impl.TopicServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicServiceImpl topicService;

    @Test
    public void findAll_Success() {
        List<Topic> topics = new ArrayList<>();
        Topic topic1 = new Topic();
        topic1.setId(1L);
        topics.add(topic1);

        Page<GetDataTopicResponseModel> pagedResponse = new PageImpl(topics);
        Mockito.when(topicRepository.findALlWithNotCollectTopic(any())).thenReturn(pagedResponse);

        List<GetDataTopicResponseModel> result = topicService.findAll(PageRequest.of(0, 10)).getContent();

        assertEquals(1, result.size());

    }

    @Test
    public void findById_Success() {
        Topic topic = new Topic();
        topic.setId(1L);

        when(topicRepository.getDetailByTopicId(1L)).thenReturn(mockGetDataTopicResponseModel());

        GetDataTopicResponseModel result = topicService.findById(1L);

        assertEquals(topic.getId(), result.getId());
    }

    @Test
    public void findTopicHistoryByTopicId_Success() {

        List<GetTopicHistoryResponseModel> topicHistory = new ArrayList<>();
        topicHistory.add(mockTopicHistory());

        when(topicRepository.getTopicHistoryByTopicId(any())).thenReturn(topicHistory);

        List<GetTopicHistoryResponseModel> result = topicService.findTopicHistoryByTopicId(any());

        assertEquals(1, result.size());
    }

    @Test
    public void testAddTopic_Success() {

        Authentication authentication = mock(Authentication.class);
        User user = new User();
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AddTopicRequestModel request = new AddTopicRequestModel();
        request.setSubject("Test Subject");
        request.setDescription("Test Description");
        request.setStatus("Test Status");

        when(topicRepository.saveAndFlush(any(Topic.class))).thenReturn(new Topic());

        boolean result = topicService.addTopic(request);

        verify(topicRepository).saveAndFlush(any(Topic.class));

        assertTrue(result);
    }

    @Test
    public void testAddTopic_Failure() {

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenThrow(new RuntimeException()); // Simulate exception
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AddTopicRequestModel request = new AddTopicRequestModel();

        boolean result = topicService.addTopic(request);

        assertFalse(result);
    }

    @Test
    public void editDataTopic_Success() {
        EditDataTopicRequestModel request = new EditDataTopicRequestModel();
        request.setId(1L);
        request.setSubject("Updated Subject");
        request.setDescription("Updated Description");
        request.setStatus("To Do");

        Topic topic = new Topic();
        TopicDetail topicDetail = new TopicDetail();
        topic.setTopicDetail(topicDetail);

        when(topicRepository.findById(request.getId())).thenReturn(Optional.of(topic));
        when(topicRepository.saveAndFlush(any())).thenReturn(new Topic());

        assertTrue(topicService.editDataTopic(request));
    }

    @Test
    public void editDataTopic_Failure_TopicNotFound() {
        EditDataTopicRequestModel request = new EditDataTopicRequestModel();
        request.setId(1L);

        when(topicRepository.findById(request.getId())).thenReturn(Optional.empty());

        assertFalse(topicService.editDataTopic(request));
    }

    @Test
    public void collectTopic_Success() {
        Long id = 1L;

        Topic topic = new Topic();
        topic.setCollect("N");

        when(topicRepository.findById(id)).thenReturn(Optional.of(topic));
        when(topicRepository.saveAndFlush(any())).thenReturn(new Topic());

        assertTrue(topicService.collectTopic(id));
    }

    @Test
    public void collectTopic_Failure_TopicNotFound() {
        Long id = 1L;

        when(topicRepository.findById(id)).thenReturn(Optional.empty());

        assertFalse(topicService.collectTopic(id));
    }

    private GetDataTopicResponseModel mockGetDataTopicResponseModel() {
        GetDataTopicResponseModel data = new GetDataTopicResponseModel() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getSubject() {
                return "test";
            }

            @Override
            public String getDescription() {
                return "test";
            }

            @Override
            public String getStatus() {
                return "To Do";
            }

            @Override
            public String getCreateBy() {
                return "jakkarin";
            }

            @Override
            public String getEmail() {
                return "a@a.com";
            }

            @Override
            public String getCreateDate() {
                return "2024-03-03";
            }
        };
        return data;
    }
    private GetTopicHistoryResponseModel mockTopicHistory() {
        GetTopicHistoryResponseModel data = new GetTopicHistoryResponseModel() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getSubject() {
                return "test";
            }

            @Override
            public String getDescription() {
                return "test";
            }

            @Override
            public String getStatus() {
                return "To Do";
            }

            @Override
            public String getCreateBy() {
                return "jakkarin";
            }

            @Override
            public String getCreateDateTime() {
                return "2021-02-02";
            }
        };

        return data;
    }
}
