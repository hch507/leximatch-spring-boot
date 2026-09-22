package org.leximatch.game.api;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.response.NoticeResponse;
import org.leximatch.game.application.service.NoticeService;
import org.leximatch.game.common.api.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public Api<NoticeResponse> notice(){
        NoticeResponse response = noticeService.getTodayNotice();

        return Api.OK(response);
    }
}
