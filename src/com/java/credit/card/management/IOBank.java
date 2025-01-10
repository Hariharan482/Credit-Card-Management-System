package com.java.credit.card.management;

class IOBank extends Bank{

    IOBank(){
        setBankName("IOB Bank");
        addCardType(new CardType("Infinity",50000));
        addCardType(new CardType("Prestige",100000));
        addCardType(new CardType("Wanderer",150000));
    }
}
