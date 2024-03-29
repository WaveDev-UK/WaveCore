package dev.wave.wavecore.sql;

import dev.wave.wavecore.sql.builder.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SQLClient {

    private final Plugin plugin;
    private final String ip;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    private Connection connection;

    public SQLClient(Plugin plugin, String ip, int port, String database, String username, String password) {
        this.plugin = plugin;
        this.ip = ip;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        openConnection();
        keepAlive();
    }

    private void openConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"
                            + ip + ":" + port + "/" + database,
                    username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void keepAlive() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, this::openConnection), 40, 40);
    }

    public void sendUpdateAsync(String update) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            sendUpdateSync(update);
        });
    }

    public void sendUpdateSync(String update){
        try {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet sendQuerySync(String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SQLRow> fetchDataMultiple(String table, String[] ids, SQLRequirement... requirements) {
        SelectBuilder queryBuilder = new SelectBuilder(table, ids);
        queryBuilder.insertRequirements(requirements);
        String query = queryBuilder.build();
        ResultSet set = sendQuerySync(query);
        List<SQLRow> data = new ArrayList<>();
        try {
            ResultSetMetaData rsmd = set.getMetaData();
            int columns = rsmd.getColumnCount();
            while (set.next()) {
                SQLRow sqlRow = new SQLRow(set.getRow());
                for (int i = 1; i <= columns; i++) {
                    String dat = set.getString(i);
                    sqlRow.add(rsmd.getColumnName(i), dat);
                }
                data.add(sqlRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void createTable(String tableName, boolean primaryKey, SQLColumn... sqlColumns) {
        CreateBuilder createBuilder = new CreateBuilder(tableName);
        for (SQLColumn sqlColumn : sqlColumns) {
            createBuilder.addColumn(sqlColumn);
        }
        sendUpdateAsync(createBuilder.build(primaryKey));
    }

    public void insert(String table, SQLData... sqlDatas) {
        InsertBuilder insertBuilder = new InsertBuilder(table);
        for (SQLData sqlData : sqlDatas) {
            insertBuilder.addData(sqlData);
        }
        sendUpdateAsync(insertBuilder.build());
    }

    public void update(String table, SQLData[] sqlData, SQLRequirement[] sqlRequirements) {
        UpdateBuilder updateBuilder = new UpdateBuilder(table, sqlData);
        updateBuilder.setRequirements(sqlRequirements);
        sendUpdateAsync(updateBuilder.build());
    }

    public void delete(String table, SQLRequirement... sqlRequirements) {
        DeleteBuilder deleteBuilder = new DeleteBuilder(table, sqlRequirements);
        sendQuerySync(deleteBuilder.build());
    }


}
