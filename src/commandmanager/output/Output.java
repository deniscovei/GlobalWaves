package commandmanager.output;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commandmanager.IoEntry;
import commandmanager.input.attributes.Stats;
import data.entities.Notification;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * The type Output.
 */
@Setter
@Getter
@JsonPropertyOrder({ "command", "user", "timestamp", "message", "results", "stats"})
public final class Output extends IoEntry {
    private String user;
    private String message;
    private List<String> results;
    private Object result;
    private Stats stats;
    private List<Notification> notifications;

    public static class Builder {
        private String builderCommand = null;
        private Integer builderTimestamp = null;
        private String builderUser = null;
        private String builderMessage = null;
        private List<String> builderResults = null;
        private Object builderResult = null;
        private Stats builderStats = null;
        private List<Notification> builderNotifications = null;

        /**
         * Instantiates a new Builder.
         */
        public Builder() {
        }

        /**
         * Command builder.
         *
         * @param command the command
         * @return the builder
         */
        public Builder command(final String command) {
            this.builderCommand = command;
            return this;
        }

        /**
         * Timestamp builder.
         *
         * @param timestamp the timestamp
         * @return the builder
         */
        public Builder timestamp(final Integer timestamp) {
            this.builderTimestamp = timestamp;
            return this;
        }

        /**
         * User builder.
         *
         * @param user the user
         * @return the builder
         */
        public Builder user(final String user) {
            this.builderUser = user;
            return this;
        }

        /**
         * Message builder.
         *
         * @param message the message
         * @return the builder
         */
        public Builder message(final String message) {
            this.builderMessage = message;
            return this;
        }

        /**
         * Results builder.
         *
         * @param results the results
         * @return the builder
         */
        public Builder results(final List<String> results) {
            this.builderResults = results;
            return this;
        }

        /**
         * Result builder.
         *
         * @param result the result
         * @return the builder
         */
        public Builder result(final Object result) {
            this.builderResult = result;
            return this;
        }

        /**
         * Stats builder.
         *
         * @param stats the stats
         * @return the builder
         */
        public Builder stats(final Stats stats) {
            this.builderStats = stats;
            return this;
        }

        /**
         * Notifications builder.
         *
         * @param notifications the notifications
         * @return the builder
         */
        public Builder notifications(final List<Notification> notifications) {
            this.builderNotifications = notifications;
            return this;
        }

        /**
         * Build output.
         *
         * @return the output
         */
        public Output build() {
            return new Output(this);
        }
    }

    private Output(final Builder builder) {
        this.command = builder.builderCommand;
        this.timestamp = builder.builderTimestamp;
        this.user = builder.builderUser;
        this.message = builder.builderMessage;
        this.results = builder.builderResults;
        this.result = builder.builderResult;
        this.stats = builder.builderStats;
        this.notifications = builder.builderNotifications;
    }
}
