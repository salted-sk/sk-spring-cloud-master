package com.sk.config.social.mysql.support;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 因mysql中rank关键字问题所以重写
 *
 * @author zhangqiao
 * @since 2020/1/7 13:17
 */
public class JdbcConnectionRepository implements ConnectionRepository {
    private final String userId;
    private final JdbcTemplate jdbcTemplate;
    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final TextEncryptor textEncryptor;
    private final String tablePrefix;
    private final ServiceProviderConnectionMapper connectionMapper = new ServiceProviderConnectionMapper();

    public JdbcConnectionRepository(String userId, JdbcTemplate jdbcTemplate, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor, String tablePrefix) {
        this.userId = userId;
        this.jdbcTemplate = jdbcTemplate;
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
        this.tablePrefix = tablePrefix;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findAllConnections() {
        List<Connection<?>> resultList = this.jdbcTemplate.query(this.selectFromUserConnection() + " where userId = ? order by providerId, sequence", this.connectionMapper, new Object[]{this.userId});
        MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap();
        Set<String> registeredProviderIds = this.connectionFactoryLocator.registeredProviderIds();
        Iterator var4 = registeredProviderIds.iterator();

        while(var4.hasNext()) {
            String registeredProviderId = (String)var4.next();
            connections.put(registeredProviderId, Collections.emptyList());
        }

        String providerId;
        Connection connection;
        for(var4 = resultList.iterator(); var4.hasNext(); connections.add(providerId, connection)) {
            connection = (Connection)var4.next();
            providerId = connection.getKey().getProviderId();
            if (((List)connections.get(providerId)).size() == 0) {
                connections.put(providerId, new LinkedList());
            }
        }

        return connections;
    }

    @Override
    public List<Connection<?>> findConnections(String providerId) {
        return this.jdbcTemplate.query(this.selectFromUserConnection() + " where userId = ? and providerId = ? order by sequence", this.connectionMapper, new Object[]{this.userId, providerId});
    }

    @Override
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
        List<?> connections = this.findConnections(this.getProviderId(apiType));
        return (List<Connection<A>>) connections;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUsers) {
        if (providerUsers != null && !providerUsers.isEmpty()) {
            StringBuilder providerUsersCriteriaSql = new StringBuilder();
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("userId", this.userId);
            Iterator it = providerUsers.entrySet().iterator();

            while(it.hasNext()) {
                Map.Entry<String, List<String>> entry = (Map.Entry)it.next();
                String providerId = (String)entry.getKey();
                providerUsersCriteriaSql.append("providerId = :providerId_").append(providerId).append(" and providerUserId in (:providerUserIds_").append(providerId).append(")");
                parameters.addValue("providerId_" + providerId, providerId);
                parameters.addValue("providerUserIds_" + providerId, entry.getValue());
                if (it.hasNext()) {
                    providerUsersCriteriaSql.append(" or ");
                }
            }

            List<Connection<?>> resultList = (new NamedParameterJdbcTemplate(this.jdbcTemplate)).query(this.selectFromUserConnection() + " where userId = :userId and " + providerUsersCriteriaSql + " order by providerId, sequence", parameters, this.connectionMapper);
            MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap();
            Iterator var16 = resultList.iterator();

            while(var16.hasNext()) {
                Connection<?> connection = (Connection)var16.next();
                String providerId = connection.getKey().getProviderId();
                List<String> userIds = (List)providerUsers.get(providerId);
                List<Connection<?>> connections = (List)connectionsForUsers.get(providerId);
                if (connections == null) {
                    connections = new ArrayList(userIds.size());

                    for(int i = 0; i < userIds.size(); ++i) {
                        ((List)connections).add((Object)null);
                    }

                    connectionsForUsers.put(providerId, connections);
                }

                String providerUserId = connection.getKey().getProviderUserId();
                int connectionIndex = userIds.indexOf(providerUserId);
                ((List)connections).set(connectionIndex, connection);
            }

            return connectionsForUsers;
        } else {
            throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
        }
    }

    @Override
    public Connection<?> getConnection(ConnectionKey connectionKey) {
        try {
            return this.jdbcTemplate.queryForObject(this.selectFromUserConnection() + " where userId = ? and providerId = ? and providerUserId = ?", this.connectionMapper, new Object[]{this.userId, connectionKey.getProviderId(), connectionKey.getProviderUserId()});
        } catch (EmptyResultDataAccessException var3) {
            throw new NoSuchConnectionException(connectionKey);
        }
    }

    @Override
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
        String providerId = this.getProviderId(apiType);
        return (Connection<A>) this.getConnection(new ConnectionKey(providerId, providerUserId));
    }

    @Override
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
        String providerId = this.getProviderId(apiType);
        Connection<A> connection = (Connection<A>) this.findPrimaryConnection(providerId);
        if (connection == null) {
            throw new NotConnectedException(providerId);
        } else {
            return connection;
        }
    }

    @Override
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        String providerId = this.getProviderId(apiType);
        return (Connection<A>) this.findPrimaryConnection(providerId);
    }

    @Override
    @Transactional
    public void addConnection(Connection<?> connection) {
        try {
            ConnectionData data = connection.createData();
            int sequence = (Integer)this.jdbcTemplate.queryForObject("select coalesce(max(sequence) + 1, 1) as sequence from " + this.tablePrefix + "UserConnection where userId = ? and providerId = ?", new Object[]{this.userId, data.getProviderId()}, Integer.class);
            this.jdbcTemplate.update("insert into " + this.tablePrefix + "UserConnection (userId, providerId, providerUserId, sequence, displayName, profileUrl, imageUrl, accessToken, secret, refreshToken, expireTime) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{this.userId, data.getProviderId(), data.getProviderUserId(), sequence, data.getDisplayName(), data.getProfileUrl(), data.getImageUrl(), this.encrypt(data.getAccessToken()), this.encrypt(data.getSecret()), this.encrypt(data.getRefreshToken()), data.getExpireTime()});
        } catch (DuplicateKeyException var4) {
            throw new DuplicateConnectionException(connection.getKey());
        }
    }

    @Override
    @Transactional
    public void updateConnection(Connection<?> connection) {
        ConnectionData data = connection.createData();
        this.jdbcTemplate.update("update " + this.tablePrefix + "UserConnection set displayName = ?, profileUrl = ?, imageUrl = ?, accessToken = ?, secret = ?, refreshToken = ?, expireTime = ? where userId = ? and providerId = ? and providerUserId = ?", new Object[]{data.getDisplayName(), data.getProfileUrl(), data.getImageUrl(), this.encrypt(data.getAccessToken()), this.encrypt(data.getSecret()), this.encrypt(data.getRefreshToken()), data.getExpireTime(), this.userId, data.getProviderId(), data.getProviderUserId()});
    }

    @Override
    @Transactional
    public void removeConnections(String providerId) {
        this.jdbcTemplate.update("delete from " + this.tablePrefix + "UserConnection where userId = ? and providerId = ?", new Object[]{this.userId, providerId});
    }

    @Override
    @Transactional
    public void removeConnection(ConnectionKey connectionKey) {
        this.jdbcTemplate.update("delete from " + this.tablePrefix + "UserConnection where userId = ? and providerId = ? and providerUserId = ?", new Object[]{this.userId, connectionKey.getProviderId(), connectionKey.getProviderUserId()});
    }

    private String selectFromUserConnection() {
        return "select userId, providerId, providerUserId, displayName, profileUrl, imageUrl, accessToken, secret, refreshToken, expireTime from " + this.tablePrefix + "UserConnection";
    }

    private Connection<?> findPrimaryConnection(String providerId) {
        List<Connection<?>> connections = this.jdbcTemplate.query(this.selectFromUserConnection() + " where userId = ? and providerId = ? order by sequence", this.connectionMapper, new Object[]{this.userId, providerId});
        return connections.size() > 0 ? (Connection)connections.get(0) : null;
    }

    private <A> String getProviderId(Class<A> apiType) {
        return this.connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
    }

    private String encrypt(String text) {
        return text != null ? this.textEncryptor.encrypt(text) : text;
    }

    private final class ServiceProviderConnectionMapper implements RowMapper<Connection<?>> {
        private ServiceProviderConnectionMapper() {
        }

        @Override
        public Connection<?> mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConnectionData connectionData = this.mapConnectionData(rs);
            ConnectionFactory<?> connectionFactory = JdbcConnectionRepository.this.connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
            return connectionFactory.createConnection(connectionData);
        }

        private ConnectionData mapConnectionData(ResultSet rs) throws SQLException {
            return new ConnectionData(rs.getString("providerId"), rs.getString("providerUserId"), rs.getString("displayName"), rs.getString("profileUrl"), rs.getString("imageUrl"), this.decrypt(rs.getString("accessToken")), this.decrypt(rs.getString("secret")), this.decrypt(rs.getString("refreshToken")), this.expireTime(rs.getLong("expireTime")));
        }

        private String decrypt(String encryptedText) {
            return encryptedText != null ? JdbcConnectionRepository.this.textEncryptor.decrypt(encryptedText) : encryptedText;
        }

        private Long expireTime(long expireTime) {
            return expireTime == 0L ? null : expireTime;
        }
    }
}

