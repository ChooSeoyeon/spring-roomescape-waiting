package roomescape.helper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DatabaseCleaner implements InitializingBean {
    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    public void execute() {
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        clearTables();
        clearReservationWaiting();
        clearReservation();
        clearMember();
        clearTime();
        clearTheme();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        entityManager.unwrap(Session.class).doWork(this::generateTableInformation);
    }

    private void generateTableInformation(Connection connection) throws SQLException {
        List<String> tableNames = new ArrayList<>();
        ResultSet tables = extractTables(connection);
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            tableNames.add(tableName);
        }
        this.tableNames = tableNames;
    }

    private static ResultSet extractTables(Connection connection) throws SQLException {
        return connection
                .getMetaData()
                .getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"});
    }

    private void clearTables() {
        for (String table : tableNames) {
            System.out.println("table = " + table);
        }
//        tables.forEach(table -> {
//            entityManager.createNativeQuery(String.format("DELETE FROM %s", table))
//                    .executeUpdate();
//            entityManager.createNativeQuery(String.format("ALTER TABLE %s ALTER COLUMN id RESTART", table))
//                    .executeUpdate();
//        });
    }

    private void clearReservationWaiting() {
        entityManager.createNativeQuery("DELETE FROM reservation_waiting").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE reservation_waiting ALTER COLUMN id RESTART").executeUpdate();
    }

    private void clearReservation() {
        entityManager.createNativeQuery("DELETE FROM reservation").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE reservation ALTER COLUMN id RESTART").executeUpdate();
    }

    private void clearMember() {
        entityManager.createNativeQuery("DELETE FROM member").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE member ALTER COLUMN id RESTART").executeUpdate();
    }

    private void clearTime() {
        entityManager.createNativeQuery("DELETE FROM reservation_time").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE reservation_time ALTER COLUMN id RESTART").executeUpdate();
    }

    private void clearTheme() {
        entityManager.createNativeQuery("DELETE FROM theme").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE theme ALTER COLUMN id RESTART").executeUpdate();
    }
}
