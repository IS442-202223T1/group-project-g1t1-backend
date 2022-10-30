package com.is442project.cpa.booking;

import lombok.Data;

@Data

public class CardOpsData {
    private String cardID;

    public CardOpsData(String cardID){
        this.cardID = cardID;
    }

    public CardOpsData(){}

    public String getCardID(){
        return cardID;
    }

    public String toString(){
        return "Card ID " + cardID;
    }
}
