package ru.matveylegenda.tidiscord2fa.configs;

import net.elytrium.serializer.annotations.Comment;
import net.elytrium.serializer.annotations.CommentValue;
import net.elytrium.serializer.annotations.NewLine;
import net.elytrium.serializer.language.object.YamlSerializable;

import java.util.List;

public class MessagesConfig extends YamlSerializable {
    public static MessagesConfig instance = new MessagesConfig();

    public Discord discord = new Discord();

    public static class Discord {
        public VerifyMessage verifyMessage = new VerifyMessage();

        public static class VerifyMessage {
            public String content = "Подтвердите вход на сервер";

            public Buttons buttons = new Buttons();

            public static class Buttons {
                public Allow allow = new Allow();

                public static class Allow {
                    public String emoji = "✅";
                    public String content = "Подтвердить";
                }
            }
        }

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое при подтверждении входа")
        })
        public String allowJoin = "Вход подтвержден";

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое если игрок уже подтвердил вход")
        })
        public String verifyNotRequired = "Подтверждение не требуется";

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое если игрок не найден на сервере")
        })
        public String playerNotFound = "Аккаунт не найден";

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое при привязке аккаунта")
        })
        public String accountLinked = "Соц. сеть успешно привязана к аккаунту {player}";

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое при отвязке аккаунта")
        })
        public String accountUnlinked = "Discord успешно отвязан от аккаунта {player}";
    }

    public Minecraft minecraft = new Minecraft();

    @NewLine
    public static class Minecraft {
        @Comment({
                @CommentValue(" {prefix} - Префикс перед сообщениями")
        })
        public String prefix = "&#8833ECD&#8544EAi&#8254E9s&#7F65E7c&#7C75E6o&#7A86E4r&#7796E3d&#74A7E12&#71B7E0F&#6EC8DEA &8»";

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое при входе на сервер")
        })
        public List<String> join = List.of(
                "",
                " {prefix} &fПодтвердите вход через &#8833ECDiscord",
                ""
        );

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое при подтверждении входа")
        })
        public List<String> allowJoin = List.of(
                "",
                " {prefix} &fВход успешно выполнен!",
                ""
        );

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое при вводе команды без аргументов")
        })
        public List<String> usage = List.of(
                "",
                "Команды:",
                "/2fa link - привязать аккаунт",
                ""
        );

        @Comment({
                @CommentValue(" Сообщение отправляемое при перезагрузке конфига")
        })
        public List<String> reload = List.of(
                "",
                "{prefix} &fКонфиг перезагружен!",
                ""
        );

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое при привязке Discord")
        })
        public List<String> link = List.of(
                "",
                "{prefix} &fКод - {code}. Отправьте его боту example#1111",
                ""
        );

        @Comment({
                @CommentValue(" Сообщение отправляемое при попытке привязки с уже существующей привязкой")
        })
        public List<String> alreadyLinked = List.of(
                "",
                "{prefix} &fАккаунт уже привязан",
                ""
        );
    }
}
