package com.is442project.cpa.booking;

import lombok.Data;

@Data

public class CardOpsData {
    private Long cardID;

    public CardOpsData(Long cardID){
        this.cardID = cardID;
    }

    public Long getCardID(){
        return cardID;
    }
}
