package oop.tercerparcial;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GameRecordsTest {

    private File recordsFile;

    @Before
    public void prepareTest() throws IOException {
        recordsFile = File.createTempFile("records", ".csv");
        System.out.println("Records for test will be stored to file: " + recordsFile.getAbsolutePath());
    }

    @Test
    public void givenANewRecord_whenSaveRecord_then1RecordIsStoredToFile() throws IOException {
        // Given:
        RecordsManager recordsManager = new RecordsManager(recordsFile);
        GameRecord record = new GameRecord();

        record.setPlayerName("Mario");
        record.setScore(100);

        // When:
        recordsManager.save(record);

        // Then:
        validateRecordsInFile(Collections.singletonList(record));

    }

    @Test
    public void given5SavedNewRecords_whenListRecord_thenRecordsAreSortedByScoreDesc() throws IOException {
        // Given:
        RecordsManager recordsManager = new RecordsManager(recordsFile);
        List<GameRecord> inputRecords = Arrays.asList(
                new GameRecord(100, "Juan"),
                new GameRecord(110, "Ana"),
                new GameRecord(50, "Pedro"),
                new GameRecord(105, "Beto"),
                new GameRecord(115, "Pablo"));

        for (GameRecord record : inputRecords) {
            recordsManager.save(record);
        }

        // When:
        List<GameRecord> records = recordsManager.list();

        // Then:
        validateRecordsInFile(inputRecords);

        assertEquals("5 records expected in the list", 5, records.size());
        assertEquals("Expected Pablo with 115 points", new GameRecord(115, "Pablo"), records.get(0));
        assertEquals("Expected Pablo with 110 points", new GameRecord(110, "Ana"), records.get(1));
        assertEquals("Expected Pablo with 105 points", new GameRecord(105, "Beto"), records.get(2));
        assertEquals("Expected Pablo with 100 points", new GameRecord(100, "Juan"), records.get(3));
        assertEquals("Expected Pablo with 50 points", new GameRecord(50, "Pedro"), records.get(4));

    }

    @Test(expected = NegativeScoreException.class)
    public void givenARecordWithNegativeScore_whenSaveRecord_thenExceptionIsThrown() {
        // Given:
        RecordsManager recordsManager = new RecordsManager(recordsFile);
        GameRecord record = new GameRecord(-100, "Juan");

        // When:
        recordsManager.save(record);

        // Then: NegativeScoreException was thrown
    }

    @Test(expected = InvalidPlayerNameException.class)
    public void givenARecordWithPlayerNameIsNull_whenSaveRecord_thenExceptionIsThrown() {
        // Given:
        RecordsManager recordsManager = new RecordsManager(recordsFile);
        GameRecord record = new GameRecord();

        record.setScore(100);

        // When:
        recordsManager.save(record);

        // Then: NegativeScoreException was thrown
    }

    @Test(expected = InvalidPlayerNameException.class)
    public void givenARecordWithPlayerNameIsEmpty_whenSaveRecord_thenExceptionIsThrown() {
        // Given:
        RecordsManager recordsManager = new RecordsManager(recordsFile);
        GameRecord record = new GameRecord();

        record.setScore(100);
        record.setPlayerName("");

        // When:
        recordsManager.save(record);

        // Then: NegativeScoreException was thrown
    }

    private void validateRecordsInFile(List<GameRecord> records) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get(recordsFile.getAbsolutePath()));
        Iterator<String> linesIterator = lines.iterator();
        Iterator<GameRecord> recordsIterator = records.iterator();

        while (linesIterator.hasNext() && recordsIterator.hasNext()) {
            String csvLine = linesIterator.next();
            GameRecord record = recordsIterator.next();

            assertEquals("csvLine does not match the record", csvLine, record.getScore() + "," + record.getPlayerName());
        }

        assertFalse("No more lines are expected", linesIterator.hasNext());
        assertFalse("No more records are expected", recordsIterator.hasNext());

    }

}
