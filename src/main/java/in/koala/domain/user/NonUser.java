package in.koala.domain.user;

import in.koala.enums.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class NonUser extends User{

    @Builder
    public NonUser(UserType user_type){
        this.user_type = user_type;
    }
}
