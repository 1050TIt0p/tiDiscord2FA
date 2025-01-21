package ru.matveylegenda.tidiscord2fa.configs;

import net.elytrium.serializer.annotations.Comment;
import net.elytrium.serializer.annotations.CommentValue;
import net.elytrium.serializer.annotations.NewLine;
import net.elytrium.serializer.language.object.YamlSerializable;

import java.util.List;

public class MainConfig extends YamlSerializable {
    public static MainConfig instance = new MainConfig();

    @Comment({
            @CommentValue(" Команда (на сервере) для привязки (после изменения требуется перезагрузить сервер)")
    })
    public String command = "2fa";

    @NewLine
    @Comment({
            @CommentValue(" Команда (в Discord) для отвязки (после изменения требуется перезагрузить сервер)")
    })
    public String unlinkCommand = "отвязать";

    @Comment({
            @CommentValue(" Описание команды (в Discord) для отвязки (после изменения требуется перезагрузить сервер)")
    })
    public String unlinkDescription = "Отвязать аккаунт";

    @NewLine
    @Comment({
            @CommentValue(" Команда (в Discord) для просмотра привязанных аккаунтов (после изменения требуется перезагрузить сервер)")
    })
    public String accountsCommand = "аккаунты";

    @Comment({
            @CommentValue(" Описание команды (в Discord) для просмотра привязанных аккаунтов (после изменения требуется перезагрузить сервер)")
    })
    public String accountsDescription = "Показать список привязанных аккаунтов";

    public Discord discord = new Discord();

    @NewLine
    public static class Discord {
        @Comment({
                @CommentValue(" Токен дискорд бота")
        })
        public String token = "TOKEN";
    }

    @NewLine
    @Comment({
            @CommentValue(" Максимальное количество привязанных аккаунтов к Discord")
    })
    public int maxLinkAccounts = 3;

    @NewLine
    @Comment({
            @CommentValue(" Время в секундах через которое игрока кикнет если он не подтвердит вход")
    })
    public int time = 30;

    @NewLine
    @Comment({
            @CommentValue(" Разрешенные команды во время подтверждения")
    })
    public List<String> allowedCommands = List.of(
            "/register",
            "/reg",
            "/login",
            "/l"
    );

    public Code code = new Code();

    @NewLine
    @Comment({
            @CommentValue(" Настройки кода для привязки")
    })
    public static class Code {
        @Comment({
                @CommentValue(" Символы которые будут содержаться в коде")
        })
        public String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        @Comment({
                @CommentValue(" Длина кода")
        })
        public int length = 6;
    }

    public Bossbar bossbar = new Bossbar();

    @NewLine
    public static class Bossbar {
        public boolean enabled = true;

        @NewLine
        @Comment({
                @CommentValue(" Текст боссбара")
        })
        public String title = "Осталось &c{time} &fсекунд";

        @NewLine
        @Comment({
                @CommentValue(" Цвет боссбара")
        })
        public String color = "RED";

        @NewLine
        @Comment({
                @CommentValue(" Стиль боссбара")
        })
        public String style = "SEGMENTED_12";
    }

    @NewLine
    @Comment({
            @CommentValue(" Команда которая будет выполняться если игрок не успел подтвердить вход")
    })
    public String timeoutCommand = "kick {player} &cВы не успели подтвердить вход!";
}
