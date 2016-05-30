package com.helloit.householdtracker.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */

public class SchemaManager {

    private static final Logger LOGGER = LogManager.getLogger(SchemaManager.class);
    private static final String DEFAULT_DELIMITER = ";";
    private static final Pattern NEW_DELIMITER_PATTERN = Pattern.compile("(?:--|\\/\\/|\\#)?!DELIMITER=(.+)");
    private static final Pattern COMMENT_PATTERN = Pattern.compile("^(?:--|\\/\\/|\\#).+");

    final Properties properties = new Properties();

    public SchemaManager() {
        initProperties();
    }

    public static void main(final String[] args) {
        final SchemaManager schemaManager = new SchemaManager();

        schemaManager.recreateSchema();
    }

    public static void runScript(Connection connection, InputStream scriptInputStream) throws SQLException, IOException {
        try (BufferedReader scriptReader = new BufferedReader(new InputStreamReader(scriptInputStream))) {
            StringBuffer command = null;
            String delimiter = DEFAULT_DELIMITER;
            String line;

            while ((line = scriptReader.readLine()) != null) {
                if (command == null) {
                    command = new StringBuffer();
                }

                String trimmedLine = line.trim();

                Matcher delimiterMatcher = NEW_DELIMITER_PATTERN.matcher(trimmedLine);
                Matcher commentMatcher = COMMENT_PATTERN.matcher(trimmedLine);

                if (delimiterMatcher.find()) {
                    delimiter = delimiterMatcher.group(1);
                    LOGGER.info("SQL (new delimiter): " + delimiter);
                } else if (commentMatcher.find()) {
                    LOGGER.info("SQL (comment): " + trimmedLine);
                } else {
                    command.append(trimmedLine);
                    command.append(" ");

                    if (trimmedLine.endsWith(delimiter)) {
                        LOGGER.info("SQL: " + command);

                        Statement statement = connection.createStatement();

                        statement.execute(command.toString());
                        statement.close();

                        command = null;
                    }
                }
            }
        }
    }

    public void doMain() {
        recreateSchema();
    }

    private void initProperties() {
        final ClassLoader classLoader = SchemaManager.class.getClassLoader();

        try (final InputStream resourceAsStream = classLoader.getResourceAsStream("app.properties")) {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public void recreateSchema() {

        final String jdbUrl = properties.getProperty("connection.string");
        LOGGER.info(jdbUrl);

        final ClassLoader classLoader = SchemaManager.class.getClassLoader();

        try (final Connection connection = DriverManager.getConnection(jdbUrl);
             final InputStream stream = classLoader.getResourceAsStream("createSchema.sql")) {
            runScript(connection, stream);
        } catch (IOException | SQLException e) {
            LOGGER.error(e);
        }


    }

}
