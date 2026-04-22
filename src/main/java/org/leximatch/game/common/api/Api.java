package org.leximatch.game.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Todo : Null 체크 및 Valid 추가 필요
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Api<T> {

    private String resultCode;
    private String resultMsg;
    private Payload<T> payload;
}
