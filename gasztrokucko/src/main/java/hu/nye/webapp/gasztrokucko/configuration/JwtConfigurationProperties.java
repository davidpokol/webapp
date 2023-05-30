package hu.nye.webapp.gasztrokucko.configuration;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@NoArgsConstructor
@AllArgsConstructor
public class JwtConfigurationProperties {

    private String secret;
    private String issuer;
    private int tokenValidityInMinutes;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public int getTokenValidityInMinutes() {
        return tokenValidityInMinutes;
    }

    public void setTokenValidityInMinutes(int tokenValidityInMinutes) {
        this.tokenValidityInMinutes = tokenValidityInMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        JwtConfigurationProperties that = (JwtConfigurationProperties) o;

        return new EqualsBuilder().append(tokenValidityInMinutes, that.tokenValidityInMinutes).append(secret, that.secret).append(issuer, that.issuer).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(secret).append(issuer).append(tokenValidityInMinutes).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("secret", secret)
                .append("issuer", issuer)
                .append("tokenValidityInMinutes", tokenValidityInMinutes)
                .toString();
    }

}
