package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.api.DeathNoteImpl;

class TestDeathNote {
    private DeathNote deathNote;
    
    @BeforeEach 
    void setup () {
        deathNote = new DeathNoteImpl();
    }

    @Test 
    void zeroAndNegativeRules() {
        final var e = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                deathNote.getRule(0);
                deathNote.getRule(-1);
            }
        });
        assertNotNull(e.getMessage()); 
        assertFalse(e.getMessage().isBlank() || e.getMessage().isEmpty());
    }

    @Test 
    
}