package oop.tercerparcial;

import java.util.Comparator;

public class GameRecordComparator implements Comparator<GameRecord> {
    @Override
    public int compare(GameRecord o1, GameRecord o2) {
        return o2.getScore() - o1.getScore();
    }
}
