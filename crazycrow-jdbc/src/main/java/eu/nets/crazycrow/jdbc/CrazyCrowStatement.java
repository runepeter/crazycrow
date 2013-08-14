package eu.nets.crazycrow.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrazyCrowStatement implements Statement {

    private final Logger logger = LoggerFactory.getLogger(CrazyCrowStatement.class);

    private final Statement delegate;
    private final CrazyCrowConnection connection;

    public CrazyCrowStatement(final Statement delegate, final CrazyCrowConnection connection) {
        this.delegate = delegate;
        this.connection = connection;
    }

    CrazyCrowDataSource getDataSource() {
        return connection.getDataSource();
    }

    @Override
    public ResultSet executeQuery(final String sql) throws SQLException {
        return new CrazyCrowResultSet(delegate.executeQuery(sql), this);
    }

    @Override
    public int executeUpdate(final String sql) throws SQLException {

        int count = delegate.executeUpdate(sql);

        if (getDataSource().isEnabled() && getDataSource().getUpdateDelayMs() > 0) {
            try {
                logger.info("Delaying database update... [{}]", sql);
                Thread.sleep(getDataSource().getUpdateDelayMs());
            } catch (InterruptedException e) {
                logger.info("Unable to delay update.", e);
            }
        }

        return count;
    }

    @Override
    public void close() throws SQLException {
        delegate.close();
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return delegate.getMaxFieldSize();
    }

    @Override
    public void setMaxFieldSize(final int max) throws SQLException {
        delegate.setMaxFieldSize(max);
    }

    @Override
    public int getMaxRows() throws SQLException {
        return delegate.getMaxRows();
    }

    @Override
    public void setMaxRows(final int max) throws SQLException {
        delegate.setMaxRows(max);
    }

    @Override
    public void setEscapeProcessing(final boolean enable) throws SQLException {
        delegate.setEscapeProcessing(enable);
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return delegate.getQueryTimeout();
    }

    @Override
    public void setQueryTimeout(final int seconds) throws SQLException {
        delegate.setQueryTimeout(seconds);
    }

    @Override
    public void cancel() throws SQLException {
        delegate.cancel();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return delegate.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        delegate.clearWarnings();
    }

    @Override
    public void setCursorName(final String name) throws SQLException {
        delegate.setCursorName(name);
    }

    @Override
    public boolean execute(final String sql) throws SQLException {
        return delegate.execute(sql);
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return new CrazyCrowResultSet(delegate.getResultSet(), this);
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return delegate.getUpdateCount();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return delegate.getMoreResults();
    }

    @Override
    public void setFetchDirection(final int direction) throws SQLException {
        delegate.setFetchDirection(direction);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return delegate.getFetchDirection();
    }

    @Override
    public void setFetchSize(final int rows) throws SQLException {
        delegate.setFetchSize(rows);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return delegate.getFetchSize();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return delegate.getResultSetConcurrency();
    }

    @Override
    public int getResultSetType() throws SQLException {
        return delegate.getResultSetType();
    }

    @Override
    public void addBatch(final String sql) throws SQLException {
        delegate.addBatch(sql);
    }

    @Override
    public void clearBatch() throws SQLException {
        delegate.clearBatch();
    }

    @Override
    public int[] executeBatch() throws SQLException {

        if (getDataSource().isEnabled() && getDataSource().getUpdateDelayMs() > 0) {
            try {
                Thread.sleep(getDataSource().getUpdateDelayMs());
            } catch (InterruptedException e) {
                logger.error("Unable to delay batch update.", e);
            }
        }

        return delegate.executeBatch();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }

    @Override
    public boolean getMoreResults(final int current) throws SQLException {
        return delegate.getMoreResults(current);
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return new CrazyCrowResultSet(delegate.getGeneratedKeys(), this);
    }

    @Override
    public int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
        return delegate.executeUpdate(sql, autoGeneratedKeys);
    }

    @Override
    public int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
        return delegate.executeUpdate(sql, columnIndexes);
    }

    @Override
    public int executeUpdate(final String sql, final String[] columnNames) throws SQLException {
        return delegate.executeUpdate(sql, columnNames);
    }

    @Override
    public boolean execute(final String sql, final int autoGeneratedKeys) throws SQLException {
        return delegate.execute(sql, autoGeneratedKeys);
    }

    @Override
    public boolean execute(final String sql, final int[] columnIndexes) throws SQLException {
        return delegate.execute(sql, columnIndexes);
    }

    @Override
    public boolean execute(final String sql, final String[] columnNames) throws SQLException {
        return delegate.execute(sql, columnNames);
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return delegate.getResultSetHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return delegate.isClosed();
    }

    @Override
    public void setPoolable(final boolean poolable) throws SQLException {
        delegate.setPoolable(poolable);
    }

    @Override
    public boolean isPoolable() throws SQLException {
        return delegate.isPoolable();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        delegate.closeOnCompletion();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return delegate.isCloseOnCompletion();
    }

    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        return delegate.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        return delegate.isWrapperFor(iface);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final CrazyCrowStatement that = (CrazyCrowStatement) o;

        if (!connection.equals(that.connection)) {
            return false;
        }
        if (!delegate.equals(that.delegate)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = delegate.hashCode();
        result = 31 * result + connection.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CrazyCrowStatement@" + hashCode() + "{" +
                "delegate=" + delegate +
                '}';
    }
}
