package com.vdthai.vdeopoker;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vdthai on 2016-10-25.
 */

public class CheckHand {
    private List<Integer> rankList;
    private List<Integer> suitList;

    CheckHand(){
        rankList = new ArrayList<>();
        suitList = new ArrayList<>();

        /**
         * Initialize the lists.
         */
        for( int i = 0; i < 14; i++ ){
            rankList.add(i,0);
        }

        for( int i = 0; i < 5; i++ ){
            suitList.add(i,0);
        }
    }

    /**
     * Function to check the hand.
     * Algorithm: Keep a list of suits and a list of ranks filled with 0.
     *            Then check if there exists any given number in the lists.
     *            Ex: if suitList contains 5, that means that there is at least
     *            a flush.
     * @return pair of integer (amount won) and string (winning hand).
     */
    Pair<Integer, String> checkHand( List<Card> cardList ) {
        String currentHand = "";
        int winCash = 0;

        /**
         * Loop the cardList and increment based on the rank and suit.
         * Check the Card class for the valid values.
         */
        for( Card card : cardList ){
            rankList.set(card.getRank(), rankList.get(card.getRank()) + 1);
            suitList.set(card.getSuitRank(), rankList.get(card.getSuitRank()) + 1);
        }

        /**
         * Start to check the hand for combinations.
         */
        if( isStraight( cardList ) ){
            if( suitList.contains(5) ){
                if( rankList.get(0) == 1 && rankList.get(12) == 1 ){
                    currentHand = "ROYAL FLUSH";
                    winCash += 250;
                } else {
                    currentHand = "STRAIGHT FLUSH";
                    winCash += 150;
                }
            } else {
                currentHand = "STRAIGHT";
                winCash += 20;
            }
        } else if( suitList.contains(5)) {
            currentHand = "FLUSH";
            winCash += 35;
        } else if( rankList.contains(4) ){
            currentHand = "FOUR OF A KIND";
            winCash += 100;
        } else if( rankList.contains(3) && rankList.contains(2) ){
            currentHand = "FULL HOUSE";
            winCash += 50;
        } else if( rankList.contains(3) ){
            currentHand = "THREE OF A KIND";
            winCash += 10;
        } else if( rankList.contains(2) && ( Collections.frequency( rankList, 2 ) > 1 ) ){ // Two pair?
            currentHand = "TWO PAIR";
            winCash += 5;
        } else if( rankList.contains(2) ){ // Get value of element in array that contains 2, compare to JJ
            if( rankList.indexOf( 2 ) == 0 || rankList.indexOf( 2 ) > 9 ){
                currentHand = "JACKS OR BETTER";
                winCash += 1;
            }
        }
        return new Pair<Integer, String>( winCash, currentHand );
    }

    /**
     * Check if the hand is a straight.
     * Algorithm: Add the ranks of the hand to a list. Sort the list
     *            descending and check if it is a straight.
     * @return true if the hand contains a straight.
     */
    private Boolean isStraight( List<Card> cardList ){
        List<Integer> tmpList = new ArrayList<>();
        for( Card card : cardList ){
            tmpList.add( card.getRank() );
        }
        Collections.sort( tmpList );
        for( int i = 0; i < 4; i++ ){
            if( tmpList.get( i )+1 != tmpList.get( i+1 ) ){
                return false;
            }
        }
        return true;
    }
}
