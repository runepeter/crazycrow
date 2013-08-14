package eu.nets.crazycrow.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import javax.sql.DataSource;

import eu.nets.crazycrow.Crow;

public class CrazyCrowDataSource implements DataSource, CrazyCrowDataSourceMBean {

    private final DataSource delegate;

    private Status status = Status.ENABLED;

    private final AtomicLong queryDelayMs = new AtomicLong(0);
    private final AtomicLong updateDelayMs = new AtomicLong(0);

    public CrazyCrowDataSource(final DataSource delegate) {
        this.delegate = delegate;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new CrazyCrowConnection(delegate.getConnection(), this);
    }

    @Override
    public Connection getConnection(final String username, final String password) throws SQLException {
        return new CrazyCrowConnection(delegate.getConnection(username, password), this);
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
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final CrazyCrowDataSource that = (CrazyCrowDataSource) o;

        if (!delegate.equals(that.delegate)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
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

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public boolean isEnabled() {
        return Status.ENABLED == status;
    }

    @Override
    public boolean enable() {
        this.status = Status.ENABLED;
        return true;
    }

    @Override
    public boolean disable() {
        this.status = Status.DISABLED;
        return true;
    }

    @Override
    public void setQueryDelayMs(final long units) {
        queryDelayMs.set(units);
    }

    @Override
    public long getQueryDelayMs() {
        return queryDelayMs.get();
    }

    @Override
    public void setUpdateDelayMs(final long delay) {
        updateDelayMs.set(delay);
        System.err.println("UPDATED -> " + delay);
    }

    @Override
    public long getUpdateDelayMs() {
        return updateDelayMs.get();
    }
}
