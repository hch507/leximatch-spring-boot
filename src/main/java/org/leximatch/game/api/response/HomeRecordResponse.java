package org.leximatch.game.api.response;

import java.time.LocalDate;

public record HomeRecordResponse(
        LocalDate recordDate,
        RecordResponse normalRecord,
        RecordResponse hardRecord
) {
}
