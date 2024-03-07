package dataAccess.SQL;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DatabaseManager;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Interface.GameDAO;
import model.GameData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLGameDAO extends ParentSQL implements GameDAO {
    public SQLGameDAO() throws DataAccessException {
        super();
        String[] statements = {
            """
            CREATE TABLE IF NOT EXISTS game (
            `gameID` int NOT NULL AUTO_INCREMENT,
            `whiteUsername` varchar(256),
            `blackUsername` varchar(256),
            `gameName` varchar(256),
            `game` longtext,
            PRIMARY KEY (`gameID`)
            )
           """
        };
        createDatabase(statements);

    }

    @Override
    public void createGame(GameData newGame) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()){
            String stmt = "INSERT INTO game (whiteUsername, blackUsername, gameName, game)" +
                    "VALUES (?, ?, ?, ?)";
            String gameJson = new Gson().toJson(newGame.game());
            try (PreparedStatement ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, newGame.whiteUsername());
                ps.setString(2, newGame.blackUsername());
                ps.setString(3, newGame.gameName());
                ps.setString(4, new Gson().toJson(newGame.game()));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public List<GameData> listGames() throws DataAccessException {
        List<GameData> games = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()){
            String stmt = "SELECT * FROM game";
            try (PreparedStatement ps = conn.prepareStatement(stmt)){
                try (ResultSet rs = ps.executeQuery()){
                    while (rs.next()){
                        games.add(new GameData(
                                rs.getInt("gameID"),
                                rs.getString("whiteUsername"),
                                rs.getString("blackUsername"),
                                rs.getString("gameName"),
                                new Gson().fromJson(rs.getString("game"), ChessGame.class)
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return games;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()){
            String stmt = "SELECT * FROM game WHERE gameID = ?";
            try (PreparedStatement ps = conn.prepareStatement(stmt)){
                ps.setInt(1, gameID);
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()){
                        return new GameData(
                                rs.getInt("gameID"),
                                rs.getString("whiteUsername"),
                                rs.getString("blackUsername"),
                                rs.getString("gameName"),
                                new Gson().fromJson(rs.getString("game"), ChessGame.class)
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateGame(GameData updatedGame) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()){
            String stmt = "UPDATE game SET whiteUsername = ?, blackUsername = ?, gameName = ?," +
                    "game = ? WHERE gameID = ?";
            try (PreparedStatement ps = conn.prepareStatement(stmt)){
                ps.setString(1, updatedGame.whiteUsername());
                ps.setString(2, updatedGame.blackUsername());
                ps.setString(3, updatedGame.gameName());
                ps.setString(4, new Gson().toJson(updatedGame.game()));
                ps.setInt(5, updatedGame.gameID());
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void clear() throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()){
            String stmt = "DELETE FROM game";
            try (PreparedStatement ps = conn.prepareStatement(stmt)){
                ps.executeUpdate();
            }
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
    @Override
    public int incrementGameID() throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()){
            String stmt = "SELECT MAX(gameID) AS maxID FROM game";
            try (PreparedStatement ps = conn.prepareStatement(stmt)){
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()){
                        return rs.getInt("maxID") + 1;
                    } else {
                        return 1;
                    }
                }
            }
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
}
