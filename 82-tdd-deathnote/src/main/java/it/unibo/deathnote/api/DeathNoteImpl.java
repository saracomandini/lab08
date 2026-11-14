package it.unibo.deathnote.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of the {@link DeathNote} interface.
 */
public class DeathNoteImpl implements DeathNote {

    static final long MAX_MS_CAUSE = 40L;
    static final long MAX_MS_DETAILS = 6040L;
    private final Map<String, Death> deathNote = new HashMap<>();
    private String lastName;
    private long time;

    /**
     * builds a new empty deathnote.
     */
    public DeathNoteImpl() {
        // can be implemented later
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("rule must have a valid number");
        }
        return RULES.get(ruleNumber - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name, "name can't be null");
        if (!deathNote.containsKey(name) && !name.isBlank()) {
            deathNote.put(name, new Death());
            this.lastName = name;
            this.time = System.currentTimeMillis();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDeathCause(final String cause) {
        if (!isNameWritten(this.lastName) || cause == null) {
            throw new IllegalStateException("cause can't be null or without a name");
        }
        if ((System.currentTimeMillis() - this.time) > MAX_MS_CAUSE) {
            return false;
        }
        deathNote.get(this.lastName).setCause(cause);
        this.time = System.currentTimeMillis();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDetails(final String details) {
        if (!isNameWritten(this.lastName) || details == null || details.isBlank()) {
            throw new IllegalStateException("details can't be null or without a name");
        }
        if ((System.currentTimeMillis() - this.time) > MAX_MS_DETAILS) {
            return false;
        }
        deathNote.get(this.lastName).setDetails(details);
        this.time = System.currentTimeMillis();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathCause(final String name) {
        return deathNote.get(name).cause;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathDetails(final String name) {
        return deathNote.get(name).details;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNameWritten(final String name) {
        return deathNote.containsKey(name);
    }

    private static class Death {
        private String cause;
        private String details;

        Death() {
            this.cause = "heart attack";
            this.details = " ";
        }

        private void setCause(final String cause) {
            this.cause = cause;
        }

        private void setDetails(final String details) {
            this.details = details;
        }
    }
}
