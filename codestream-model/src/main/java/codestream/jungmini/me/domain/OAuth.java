package codestream.jungmini.me.domain;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth extends BaseEntity {
    private Long oauthId;
    private String providerId;
    private OAuthProvider provider;
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OAuth oauth = (OAuth) o;
        return Objects.equals(oauthId, oauth.oauthId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oauthId);
    }
}
