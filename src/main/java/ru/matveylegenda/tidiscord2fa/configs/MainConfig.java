package ru.matveylegenda.tidiscord2fa.configs;

import net.elytrium.serializer.annotations.Comment;
import net.elytrium.serializer.annotations.CommentValue;
import net.elytrium.serializer.annotations.NewLine;
import net.elytrium.serializer.language.object.YamlSerializable;

import java.util.List;

public class MainConfig extends YamlSerializable {
    public static MainConfig instance = new MainConfig();

    @Comment({
            @CommentValue(" Команда для привязки (после изменения требуется перезагрузить сервер)")
    })
    public String command = "2fa";

    public Discord discord = new Discord();

    @NewLine
    public static class Discord {
        @Comment({
                @CommentValue(" Токен дискорд бота")
        })
        public String token = "TOKEN";

        @NewLine
        @Comment({
                @CommentValue(" Тип верификации"),
                @CommentValue(" CODE - игроку в чате отправляется код который он должен отправить боту"),
                @CommentValue(" OAUTH2 - игроку в чате отправляется ссылка по которой он привязывает аккаунт"),
                @CommentValue(" "),
                @CommentValue(" После изменения параметра перезагрузите сервер"),
        })
        public String linkType = "CODE";

        public OAuth2 oauth2 = new OAuth2();

        @NewLine
        public static class OAuth2 {
            @Comment({
                    @CommentValue(" Порт выделяемый для веб сервера")
            })
            public int port = 25569;

            @Comment({
                    @CommentValue(" Ссылка на которую будет перекидывать пользователя после авторизации в Discord"),
                    @CommentValue(" Эту же ссылку нужно указать в поле Redirects во вкладке OAuth2 на портале разработчиков Discord"),
                    @CommentValue(" КРОМЕ АЙПИ И ПОРТА НИЧЕГО НЕ МЕНЯТЬ!!!")
            })
            public String redirectUri = "http://localhost:25569/auth/discord/callback";

            @Comment({
                    @CommentValue(" Ссылка которая будет использоваться для привязки"),
                    @CommentValue(" Вместо <client_id> и <redirect_uri> подставьте client id вашего бота и redirect uri из строки выше"),
                    @CommentValue(" или сгенерируйте ссылку во вкладке OAuth2 на портале разработчиков Discord с разрешением identify")
            })
            public String oauthUri = "https://discord.com/oauth2/authorize?client_id=<client_id>&response_type=code&redirect_uri=<redirect_uri>&scope=identify";
        }
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
