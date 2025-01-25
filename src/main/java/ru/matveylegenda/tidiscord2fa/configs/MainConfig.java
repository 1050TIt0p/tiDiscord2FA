package ru.matveylegenda.tidiscord2fa.configs;

import net.elytrium.serializer.annotations.Comment;
import net.elytrium.serializer.annotations.CommentValue;
import net.elytrium.serializer.annotations.NewLine;
import net.elytrium.serializer.language.object.YamlSerializable;

import java.util.List;

public class MainConfig extends YamlSerializable {

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

    public Database database = new Database();

    @NewLine(amount = 1)
    @Comment({
            @CommentValue(" Настройки базы данных")
    })
    public static class Database {
        @Comment({
                @CommentValue(" Тип используемой базы данных"),
                @CommentValue(" Доступные варианты:"),
                @CommentValue(" SQLite"),
                @CommentValue(" MySQL")
        })
        public String type = "SQLite";

        public MySQL mysql = new MySQL();

        @NewLine(amount = 1)
        @Comment({
                @CommentValue( "Настройки MySQL")
        })
        public static class MySQL {
            public String host;
            public int port;
            public String database;
            public String user;
            public String password;
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

    public ActionBar actionbar = new ActionBar();

    @NewLine
    public static class ActionBar {
        public boolean enabled = true;

        @NewLine
        @Comment({
                @CommentValue(" Текст сообщения в actionbar")
        })
        public String actionbarMessage = "{prefix} Осталось &#8833EC{time} &fсекунд";
    }

    @NewLine
    @Comment({
            @CommentValue(" Команда которая будет выполняться если игрок не успел подтвердить вход")
    })
    public String timeoutCommand = "kick {player} &cВы не успели подтвердить вход!";


//    @Comment({
//            @CommentValue(" Command (on the server) for linking (server restart required after change)")
//    })
//    public String command = "2fa";
//
//    @NewLine
//    @Comment({
//            @CommentValue(" Command (in Discord) for unlinking (server restart required after change)")
//    })
//    public String unlinkCommand = "unlink";
//
//    @Comment({
//            @CommentValue(" Description of the unlink command (in Discord) (server restart required after change)")
//    })
//    public String unlinkDescription = "Unlink account";
//
//    @NewLine
//    @Comment({
//            @CommentValue(" Command (in Discord) for viewing linked accounts (server restart required after change)")
//    })
//    public String accountsCommand = "accounts";
//
//    @Comment({
//            @CommentValue(" Description of the accounts command (in Discord) (server restart required after change)")
//    })
//    public String accountsDescription = "Show list of linked accounts";
//
//    public Discord discord = new Discord();
//
//    @NewLine
//    public static class Discord {
//        @Comment({
//                @CommentValue(" Discord bot token")
//        })
//        public String token = "TOKEN";
//    }
//
//    public Database database = new Database();
//
//    @NewLine(amount = 1)
//    @Comment({
//            @CommentValue(" Database settings")
//    })
//    public static class Database {
//        @Comment({
//                @CommentValue(" Type of the database being used"),
//                @CommentValue(" Available options:"),
//                @CommentValue(" SQLite"),
//                @CommentValue(" MySQL")
//        })
//        public String type = "SQLite";
//
//        public MySQL mysql = new MySQL();
//
//        @NewLine(amount = 1)
//        @Comment({
//                @CommentValue(" MySQL settings")
//        })
//        public static class MySQL {
//            public String host;
//            public int port;
//            public String database;
//            public String user;
//            public String password;
//        }
//    }
//
//    @NewLine
//    @Comment({
//            @CommentValue(" Maximum number of linked accounts to Discord")
//    })
//    public int maxLinkAccounts = 3;
//
//    @NewLine
//    @Comment({
//            @CommentValue(" Time in seconds after which the player will be kicked if they do not confirm login")
//    })
//    public int time = 30;
//
//    @NewLine
//    @Comment({
//            @CommentValue(" Allowed commands during confirmation")
//    })
//    public List<String> allowedCommands = List.of(
//            "/register",
//            "/reg",
//            "/login",
//            "/l"
//    );
//
//    public Code code = new Code();
//
//    @NewLine
//    @Comment({
//            @CommentValue(" Code settings for linking")
//    })
//    public static class Code {
//        @Comment({
//                @CommentValue(" Characters that will be included in the code")
//        })
//        public String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//
//        @Comment({
//                @CommentValue(" Code length")
//        })
//        public int length = 6;
//    }
//
//    public Bossbar bossbar = new Bossbar();
//
//    @NewLine
//    public static class Bossbar {
//        public boolean enabled = true;
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Bossbar text")
//        })
//        public String title = "Remaining &c{time} &fseconds";
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Bossbar color")
//        })
//        public String color = "RED";
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Bossbar style")
//        })
//        public String style = "SEGMENTED_12";
//    }
//
//    public ActionBar actionbar = new ActionBar();
//
//    @NewLine
//    public static class ActionBar {
//        public boolean enabled = true;
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Text of the message in the actionbar")
//        })
//        public String actionbarMessage = "{prefix} Remaining &#8833EC{time} &fseconds";
//    }
//
//
//    @NewLine
//    @Comment({
//            @CommentValue(" Command that will be executed if the player did not confirm the login in time")
//    })
//    public String timeoutCommand = "kick {player} &cYou did not confirm the login in time!";
}
