package eu.nets.crazycrow.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrazyCrowPreparedStatement extends CrazyCrowStatement implements PreparedStatement {

    private final Logger logger = LoggerFactory.getLogger(CrazyCrowPreparedStatement.class);

    private final PreparedStatement delegate;
    private final CrazyCrowConnection connection;

    public CrazyCrowPreparedStatement(final PreparedStatement delegate, final CrazyCrowConnection connection) {
        super(delegate, connection);
        this.delegate = delegate;
        this.connection = connection;
    }

    CrazyCrowDataSource getDataSource() {
        return connection.getDataSource();
    }

    @Override
    public ResultSet executeQuery() throws SQLException {

        if (getDataSource().isEnabled() && getDataSource().getQueryDelayMs() > 0) {
            try {
                logger.info("Executing delayed query...");
                Thread.sleep(getDataSource().getQueryDelayMs());
            } catch (InterruptedException e) {
                logger.error("Unable to delay query.", e);
            }
        }

        return new CrazyCrowResultSet(delegate.executeQuery(), this);
    }

    @Override
    public int executeUpdate() throws SQLException {
        return delegate.executeUpdate();
    }

    @Override
    public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
        delegate.setNull(parameterIndex, sqlType);
    }

    @Override
    public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
        delegate.setBoolean(parameterIndex, x);
    }

    @Override
    public void setByte(final int parameterIndex, final byte x) throws SQLException {
        delegate.setByte(parameterIndex, x);
    }

    @Override
    public void setShort(final int parameterIndex, final short x) throws SQLException {
        delegate.setShort(parameterIndex, x);
    }

    @Override
    public void setInt(final int parameterIndex, final int x) throws SQLException {
        delegate.setInt(parameterIndex, x);
    }

    @Override
    public void setLong(final int parameterIndex, final long x) throws SQLException {
        delegate.setLong(parameterIndex, x);
    }

    @Override
    public void setFloat(final int parameterIndex, final float x) throws SQLException {
        delegate.setFloat(parameterIndex, x);
    }

    @Override
    public void setDouble(final int parameterIndex, final double x) throws SQLException {
        delegate.setDouble(parameterIndex, x);
    }

    @Override
    public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
        delegate.setBigDecimal(parameterIndex, x);
    }

    @Override
    public void setString(final int parameterIndex, final String x) throws SQLException {
        delegate.setString(parameterIndex, x);
    }

    @Override
    public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
        delegate.setBytes(parameterIndex, x);
    }

    @Override
    public void setDate(final int parameterIndex, final Date x) throws SQLException {
        delegate.setDate(parameterIndex, x);
    }

    @Override
    public void setTime(final int parameterIndex, final Time x) throws SQLException {
        delegate.setTime(parameterIndex, x);
    }

    @Override
    public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
        delegate.setTimestamp(parameterIndex, x);
    }

    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        delegate.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        delegate.setUnicodeStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        delegate.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void clearParameters() throws SQLException {
        delegate.clearParameters();
    }

    @Override
    public void setObject(final int parameterIndex, final Object x, final int targetSqlType) throws SQLException {
        delegate.setObject(parameterIndex, x, targetSqlType);
    }

    @Override
    public void setObject(final int parameterIndex, final Object x) throws SQLException {
        delegate.setObject(parameterIndex, x);
    }

    @Override
    public boolean execute() throws SQLException {
        return delegate.execute();
    }

    @Override
    public void addBatch() throws SQLException {
        delegate.addBatch();
    }

    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException {
        delegate.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setRef(final int parameterIndex, final Ref x) throws SQLException {
        delegate.setRef(parameterIndex, x);
    }

    @Override
    public void setBlob(final int parameterIndex, final Blob x) throws SQLException {
        delegate.setBlob(parameterIndex, x);
    }

    @Override
    public void setClob(final int parameterIndex, final Clob x) throws SQLException {
        delegate.setClob(parameterIndex, x);
    }

    @Override
    public void setArray(final int parameterIndex, final Array x) throws SQLException {
        delegate.setArray(parameterIndex, x);
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return delegate.getMetaData();
    }

    @Override
    public void setDate(final int parameterIndex, final Date x, final Calendar cal) throws SQLException {
        delegate.setDate(parameterIndex, x, cal);
    }

    @Override
    public void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException {
        delegate.setTime(parameterIndex, x, cal);
    }

    @Override
    public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException {
        delegate.setTimestamp(parameterIndex, x, cal);
    }

    @Override
    public void setNull(final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
        delegate.setNull(parameterIndex, sqlType, typeName);
    }

    @Override
    public void setURL(final int parameterIndex, final URL x) throws SQLException {
        delegate.setURL(parameterIndex, x);
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return delegate.getParameterMetaData();
    }

    @Override
    public void setRowId(final int parameterIndex, final RowId x) throws SQLException {
        delegate.setRowId(parameterIndex, x);
    }

    @Override
    public void setNString(final int parameterIndex, final String value) throws SQLException {
        delegate.setNString(parameterIndex, value);
    }

    @Override
    public void setNCharacterStream(final int parameterIndex, final Reader value, final long length) throws SQLException {
        delegate.setNCharacterStream(parameterIndex, value, length);
    }

    @Override
    public void setNClob(final int parameterIndex, final NClob value) throws SQLException {
        delegate.setNClob(parameterIndex, value);
    }

    @Override
    public void setClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        delegate.setClob(parameterIndex, reader, length);
    }

    @Override
    public void setBlob(final int parameterIndex, final InputStream inputStream, final long length) throws SQLException {
        delegate.setBlob(parameterIndex, inputStream, length);
    }

    @Override
    public void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        delegate.setNClob(parameterIndex, reader, length);
    }

    @Override
    public void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException {
        delegate.setSQLXML(parameterIndex, xmlObject);
    }

    @Override
    public void setObject(final int parameterIndex, final Object x, final int targetSqlType, final int scaleOrLength) throws SQLException {
        delegate.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
        delegate.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
        delegate.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        delegate.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x) throws SQLException {
        delegate.setAsciiStream(parameterIndex, x);
    }

    @Override
    public void setBinaryStream(final int parameterIndex, final InputStream x) throws SQLException {
        delegate.setBinaryStream(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader) throws SQLException {
        delegate.setCharacterStream(parameterIndex, reader);
    }

    @Override
    public void setNCharacterStream(final int parameterIndex, final Reader value) throws SQLException {
        delegate.setNCharacterStream(parameterIndex, value);
    }

    @Override
    public void setClob(final int parameterIndex, final Reader reader) throws SQLException {
        delegate.setClob(parameterIndex, reader);
    }

    @Override
    public void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException {
        delegate.setBlob(parameterIndex, inputStream);
    }

    @Override
    public void setNClob(final int parameterIndex, final Reader reader) throws SQLException {
        delegate.setNClob(parameterIndex, reader);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final CrazyCrowPreparedStatement that = (CrazyCrowPreparedStatement) o;

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
        return "CrazyCrowPreparedStatement@" + hashCode() + "{" +
                "delegate=" + delegate +
                '}';
    }
}
