package oop.tercerparcial;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;



public class RecordsManager {
    //attributes
    private File recordsFile;
    private List<GameRecord> records;




    //constructors
    public RecordsManager(File recordsFile)
    {
        this.recordsFile = recordsFile;
        records = new ArrayList<>();

    }

    //setters

    //getters

    //others
    public void save(GameRecord record)
    {
        if (record.getScore() < 0){
            throw new NegativeScoreException();
        }

        if (record.getPlayerName() == null || record.getPlayerName().isEmpty())
        {
            throw new InvalidPlayerNameException();
        }

        try(FileWriter file = new FileWriter(recordsFile, true)) {
            file.append(""+ record.getScore()).append(",").append(record.getPlayerName()).append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<GameRecord> list()
    {
        List<GameRecord> gameRecords =new ArrayList<>();

        try{
            List<String> lines = Files.readAllLines(Paths.get(recordsFile.getAbsolutePath()));

            for(String line: lines){
                GameRecord gameRecord =new GameRecord();
                String [] values = line.split(",");

                gameRecord.setScore(Integer.parseInt(values[0]));
                gameRecord.setPlayerName(values[1]);

                gameRecords.add(gameRecord);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        gameRecords.sort( new GameRecordComparator());

        return gameRecords;
    }
}
