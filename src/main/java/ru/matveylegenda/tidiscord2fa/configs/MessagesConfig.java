package ru.matveylegenda.tidiscord2fa.configs;

import net.elytrium.serializer.annotations.Comment;
import net.elytrium.serializer.annotations.CommentValue;
import net.elytrium.serializer.annotations.NewLine;
import net.elytrium.serializer.language.object.YamlSerializable;

import java.util.List;

public class MessagesConfig extends YamlSerializable {

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
                @CommentValue(" Сообщение отправляемое если игрок не найден")
        })
        public String playerNotFound = "Аккаунт не найден";

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое при привязке аккаунта")
        })
        public String accountLinked = "Discord успешно привязана к аккаунту {player}";

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое при отвязке аккаунта")
        })
        public String accountUnlinked = "Discord успешно отвязан от аккаунта {player}";

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое при попытке привязать аккаунт при достижении лимита")
        })
        public String maxLinkAccounts = "Вы достигли лимита по привязанным аккаунтам";

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое при вводе команды для просмотра аккаунтов если нет привязанных аккаунтов")
        })
        public String noLinkedAccounts = "У вас нет привязанных аккаунтов";

        @NewLine
        @Comment({
                @CommentValue(" Сообщение отправляемое при вводе команды для просмотра аккаунтов")
        })
        public String accountList = "Привязанные аккаунты:\n```{accounts}\n```";
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


//    public Discord discord = new Discord();
//
//    public static class Discord {
//        public VerifyMessage verifyMessage = new VerifyMessage();
//
//        public static class VerifyMessage {
//            public String content = "Please confirm your login on the server";
//
//            public Buttons buttons = new Buttons();
//
//            public static class Buttons {
//                public Allow allow = new Allow();
//
//                public static class Allow {
//                    public String emoji = "✅";
//                    public String content = "Confirm";
//                }
//            }
//        }
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Message sent when login is confirmed")
//        })
//        public String allowJoin = "Login confirmed";
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Message sent if the player already confirmed the login")
//        })
//        public String verifyNotRequired = "Confirmation not required";
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Message sent if the player is not found")
//        })
//        public String playerNotFound = "Account not found";
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Message sent when linking an account")
//        })
//        public String accountLinked = "Discord successfully linked to account {player}";
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Message sent when unlinking an account")
//        })
//        public String accountUnlinked = "Discord successfully unlinked from account {player}";
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Message sent when trying to link an account and reach the limit")
//        })
//        public String maxLinkAccounts = "You have reached the limit for linked accounts";
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Message sent when viewing accounts if there are no linked accounts")
//        })
//        public String noLinkedAccounts = "You have no linked accounts";
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Message sent when viewing linked accounts")
//        })
//        public String accountList = "Linked accounts:\n```{accounts}\n```";
//    }
//
//    public Minecraft minecraft = new Minecraft();
//
//    @NewLine
//    public static class Minecraft {
//        @Comment({
//                @CommentValue(" {prefix} - Prefix before messages")
//        })
//        public String prefix = "&#8833ECD&#8544EAi&#8254E9s&#7F65E7c&#7C75E6o&#7A86E4r&#7796E3d&#74A7E12&#71B7E0F&#6EC8DEA &8»";
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Message sent when joining the server")
//        })
//        public List<String> join = List.of(
//                "",
//                " {prefix} &fPlease confirm your login via &#8833ECDiscord",
//                ""
//        );
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Message sent when login is confirmed")
//        })
//        public List<String> allowJoin = List.of(
//                "",
//                " {prefix} &fLogin successfully completed!",
//                ""
//        );
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Message sent when command is entered without arguments")
//        })
//        public List<String> usage = List.of(
//                "",
//                "Commands:",
//                "/2fa link - link an account",
//                ""
//        );
//
//        @Comment({
//                @CommentValue(" Message sent when reloading the config")
//        })
//        public List<String> reload = List.of(
//                "",
//                "{prefix} &fConfig reloaded!",
//                ""
//        );
//
//        @NewLine
//        @Comment({
//                @CommentValue(" Message sent when linking Discord")
//        })
//        public List<String> link = List.of(
//                "",
//                "{prefix} &fCode - {code}. Send it to the bot example#1111",
//                ""
//        );
//
//        @Comment({
//                @CommentValue(" Message sent when trying to link an already linked account")
//        })
//        public List<String> alreadyLinked = List.of(
//                "",
//                "{prefix} &fAccount is already linked",
//                ""
//        );
//    }
}
