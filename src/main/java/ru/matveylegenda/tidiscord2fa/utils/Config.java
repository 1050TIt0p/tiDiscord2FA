package ru.matveylegenda.tidiscord2fa.utils;

import net.elytrium.serializer.annotations.Comment;
import net.elytrium.serializer.annotations.CommentValue;
import net.elytrium.serializer.annotations.NewLine;
import net.elytrium.serializer.language.object.YamlSerializable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

public class Config extends YamlSerializable {

    @Comment({
            @CommentValue(" Токен дискорд бота")
    })
    public String TOKEN = "TOKEN";

    @NewLine(amount = 1)
    @Comment({
            @CommentValue(" Добавление юзеров"),
            @CommentValue(" \"NICK\": DISCORD_ID")
    })
    public Map<String, String> users = new LinkedHashMap<>();
    {
        users.put("exampleUser", "12345");
    }

    public Settings settings = new Settings();

    @NewLine(amount = 1)
    public static class Settings {
        @Comment({
                @CommentValue(" Время в секундах через которое игрока кикнет если он не подтвердит вход")
        })
        public int time = 30;

        @NewLine(amount = 1)
        @Comment({
                @CommentValue(" Разрешенные команды во время подтверждения")
        })
        public List<String> allowedCommands = Arrays.asList("/register", "/reg", "/login", "/l");

    }

    public Messages messages = new Messages();

    @NewLine(amount = 1)
    public static class Messages {

        public Discord discord = new Discord();

        public static class Discord {
            @Comment({
                    @CommentValue(" Эмодзи кнопки")
            })
            public String buttonEmoji = "✅";

            @Comment({
                    @CommentValue(" Текст кнопки")
            })
            public String buttonText = "Подтвердить";

            @NewLine(amount = 1)
            @Comment({
                    @CommentValue(" Сообщение отправляемое при входе на сервер")
            })
            public String join = "Подтвердите вход на сервер!";

            @Comment({
                    @CommentValue(" Сообщение отправляемое при подтверждии входа")
            })
            public String allowJoin = "Вход подтвержден!";

            @NewLine(amount = 1)
            @Comment({
                    @CommentValue(" Сообщение отправляемое если игрок уже подтвердил вход")
            })
            public String verifyNotRequired = "Подтверждение не требуется";

            @NewLine(amount = 1)
            @Comment({
                    @CommentValue(" Сообщение отправляемое если игрок не найден на сервере")
            })
            public String playerNotFound = "Аккаунт не найден!";
        }

        public Minecraft minecraft = new Minecraft();

        public static class Minecraft {
            @Comment({
                    @CommentValue(" Сообщение отправляемое при входе на сервер")
            })
            public List<String> join = Arrays.asList("", " &fПодтвердите вход через &bDiscord&f!", "");

            @Comment({
                    @CommentValue(" Сообщение отправляемое при подтверждии входа")
            })
            public List<String> allowJoin = Arrays.asList("", " &fВход успешно выполнен!", "");

            @NewLine(amount = 1)
            @Comment({
                    @CommentValue(" Сообщение отправляемое при вводе команды без аргументов")
            })
            public List<String> usage = Arrays.asList("", "Команды:", "/d2fa reload - перезагрузить конфиг", "");

            @Comment({
                    @CommentValue(" Сообщение отправляемое при перезагрузке конфига")
            })
            public String reload = "&aКонфиг перезагружен!";

            @Comment({
                    @CommentValue(" Сообщение отправляемое при отсутствии прав на команду")
            })
            public String noPermission = "&cНет прав!";

            @NewLine(amount = 1)
            @Comment({
                    @CommentValue(" Сообщение при кике с сервера если игрок не успел подтвердить вход")
            })
            public String kick = "&cВы не успели подтвердить вход!";
        }
    }
}
