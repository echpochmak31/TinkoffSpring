package ru.tinkoff.edu.java.bot.service.users;

import com.pengrad.telegrambot.model.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@Component
@AllArgsConstructor
public class UserRepository implements Consumer<User> {
    private Map<Long, UserInfo> userInfoMap;
    @Override
    public void accept(@NonNull User user) {
        var info = new UserInfo(user.id(), user.username());
        userInfoMap.put(info.id(), info);
    }

    public UserInfo remove(@NonNull Long userId) {
        return userInfoMap.remove(userId);
    }
}
