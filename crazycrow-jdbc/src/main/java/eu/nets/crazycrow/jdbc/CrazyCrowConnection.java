package eu.nets.crazycrow.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class CrazyCrowConnection implements Connection {

    private final Connection delegate;
    private final CrazyCrowDataSource dataSource;

    public CrazyCrowConnection(final Connection delegate, final CrazyCrowDataSource dataSource) {
        this.delegate = delegate;
        this.dataSource = dataSource;
    }

    CrazyCrowDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return new CrazyCrowStatement(delegate.createStatement(), this);
    }

    @Override
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        return new CrazyCrowPreparedStatement(delegate.prepareStatement(sql), this);
    }

    @Override
    public CallableStatement prepareCall(final String sql) throws SQLException {
        return delegate.prepareCall(sql);
    }

    @Override
    public String nativeSQL(final String sql) throws SQLException {
        return delegate.nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(final boolean autoCommit) throws SQLException {
        delegate.setAutoCommit(autoCommit);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return delegate.getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
        delegate.commit();
    }

    @Override
    public void rollback() throws SQLException {
        delegate.rollback();
    }

    @Override
    public void close() throws SQLException {
        delegate.close();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return delegate.isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return delegate.getMetaData();
    }

    @Override
    public void setReadOnly(final boolean readOnly) throws SQLException {
        delegate.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return delegate.isReadOnly();
    }

    @Override
    public void setCatalog(final String catalog) throws SQLException {
        delegate.setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
        return delegate.getCatalog();
    }

    @Override
    public void setTransactionIsolation(final int level) throws SQLException {
        delegate.setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return delegate.getTransactionIsolation();
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
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return new CrazyCrowStatement(delegate.createStatement(resultSetType, resultSetConcurrency), this);
    }

    @Override
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return new CrazyCrowPreparedStatement(delegate.prepareStatement(sql, resultSetType, resultSetConcurrency), this);
    }

    @Override
    public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return delegate.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return delegate.getTypeMap();
    }

    @Override
    public void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
        delegate.setTypeMap(map);
    }

    @Override
    public void setHoldability(final int holdability) throws SQLException {
        delegate.setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws SQLException {
        return delegate.getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return delegate.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(final String name) throws SQLException {
        return delegate.setSavepoint(name);
    }

    @Override
    public void rollback(final Savepoint savepoint) throws SQLException {
        delegate.rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
        delegate.releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return new CrazyCrowStatement(delegate.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability), this);
    }

    @Override
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return new CrazyCrowPreparedStatement(delegate.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability), this);
    }

    @Override
    public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return delegate.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(final String sql, final int autoGeneratedKeys) throws SQLException {
        return new CrazyCrowPreparedStatement(delegate.prepareStatement(sql, autoGeneratedKeys), this);
    }

    @Override
    public PreparedStatement prepareStatement(final String sql, final int[] columnIndexes) throws SQLException {
        return new CrazyCrowPreparedStatement(delegate.prepareStatement(sql, columnIndexes), this);
    }

    @Override
    public PreparedStatement prepareStatement(final String sql, final String[] columnNames) throws SQLException {
        return new CrazyCrowPreparedStatement(delegate.prepareStatement(sql, columnNames), this);
    }

    @Override
    public Clob createClob() throws SQLException {
        return delegate.createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
        return delegate.createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return delegate.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return delegate.createSQLXML();
    }

    @Override
    public boolean isValid(final int timeout) throws SQLException {
        return delegate.isValid(timeout);
    }

    @Override
    public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
        delegate.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(final Properties properties) throws SQLClientInfoException {
        delegate.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(final String name) throws SQLException {
        return delegate.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return delegate.getClientInfo();
    }

    @Override
    public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {
        return delegate.createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {
        return delegate.createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(final String schema) throws SQLException {
        delegate.setSchema(schema);
    }

    @Override
    public String getSchema() throws SQLException {
        return delegate.getSchema();
    }

    @Override
    public void abort(final Executor executor) throws SQLException {
        delegate.abort(executor);
    }

    @Override
    public void setNetworkTimeout(final Executor executor, final int milliseconds) throws SQLException {
        delegate.setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return delegate.getNetworkTimeout();
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

        final CrazyCrowConnection that = (CrazyCrowConnection) o;

        if (!dataSource.equals(that.dataSource)) {
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
        result = 31 * result + dataSource.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CrazyCrowConnection@" + hashCode() + "{" +
                "delegate=" + delegate +
                '}';
    }
}
