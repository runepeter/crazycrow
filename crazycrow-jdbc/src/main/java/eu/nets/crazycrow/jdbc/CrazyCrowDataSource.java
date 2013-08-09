package eu.nets.crazycrow.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class CrazyCrowDataSource implements DataSource, CrazyCrowDataSourceMBean {

    private final DataSource delegate;

    public CrazyCrowDataSource(final DataSource delegate) {
        this.delegate = delegate;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new CrazyCrowConnection(delegate.getConnection());
    }

    @Override
    public Connection getConnection(final String username, final String password) throws SQLException {
        return new CrazyCrowConnection(delegate.getConnection(username, password));
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return delegate.getLogWriter();
    }

    @Override
    public void setLogWriter(final PrintWriter out) throws SQLException {
        delegate.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(final int seconds) throws SQLException {
        delegate.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return delegate.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return delegate.getParentLogger();
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
    public String toString() {
        return "CrazyCrowDataSource@" + hashCode() + "{" +
                "delegate=" + delegate +
                '}';
    }

    @Override
    public String getDomain() {
        return "eu.nets.crazycrow";
    }

    @Override
    public String getName() {
        return "DataSource";
    }

    @Override
    public String getType() {
        return CrazyCrowDataSource.class.getSimpleName();
    }
}
