package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.api.DeathNoteImpl;

class TestDeathNote {
    private static final String NAME_1 = "Sara Comandini";
    private static final String NAME_2 = "Vadym Teliatnyk";
    private static final String CAUSE_1 = "heart attack";
    private static final String CAUSE_2 = "karting accident";
    private static final String DETAIL_1 = "unknown";
    private static final String DETAIL_2 = "ran for too long";
    private static final long MS_1 = 100L;
    private static final long MS_2 = 6100L;
    private DeathNote deathNote;

    @BeforeEach 
    void setup() {
        deathNote = new DeathNoteImpl();
    }

    @Test 
    void zeroAndNegativeRules() {
        var e = assertThrows(IllegalArgumentException.class, () -> deathNote.getRule(0));
        assertNotNull(e.getMessage()); 
        assertFalse(e.getMessage().isBlank());
        e = assertThrows(IllegalArgumentException.class, () -> deathNote.getRule(-1));
        assertNotNull(e.getMessage()); 
        assertFalse(e.getMessage().isBlank());
    }

    @Test
    void noRuleIsEmptyOrNull() {
        for (int i = 1; i < DeathNote.RULES.size(); i++) {
            assertNotNull(deathNote.getRule(i));
            assertFalse(deathNote.getRule(i).isBlank());
        }
    }

    @Test
    void humanNameDie() {
        assertFalse(deathNote.isNameWritten(NAME_1));
        deathNote.writeName(NAME_1);
        assertTrue(deathNote.isNameWritten(NAME_1));
        assertFalse(deathNote.isNameWritten(NAME_2));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test 
    void causeOfDeathIsWrittenWithin40ms() throws InterruptedException {
        assertThrows(IllegalStateException.class, () -> deathNote.writeDeathCause(CAUSE_1));
        deathNote.writeName(NAME_1);
        assertEquals(CAUSE_1, deathNote.getDeathCause(NAME_1));
        deathNote.writeName(NAME_2);
        assertTrue(deathNote.writeDeathCause(CAUSE_2));
        assertEquals(CAUSE_2, deathNote.getDeathCause(NAME_2));
        Thread.sleep(MS_1);
        assertFalse(deathNote.writeDeathCause(CAUSE_1));
        assertEquals(CAUSE_2, deathNote.getDeathCause(NAME_2));
    }

    @Test
    void detailsofDeathWrittenIn6s() throws InterruptedException {
        assertThrows(IllegalStateException.class, () -> deathNote.writeDetails(DETAIL_1));
        deathNote.writeName(NAME_1);
        assertTrue(deathNote.getDeathDetails(NAME_1).isBlank());
        assertTrue(deathNote.writeDetails(DETAIL_2));
        assertEquals(DETAIL_2, deathNote.getDeathDetails(NAME_1));
        deathNote.writeName(NAME_2);
        Thread.sleep(MS_2);
        assertFalse(deathNote.writeDetails(DETAIL_1));
        assertEquals(DETAIL_2, deathNote.getDeathDetails(NAME_1));
    }

}
