package org.leximatch.game.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Payload<T> {
    private T data;
    private ApiError error;
}
